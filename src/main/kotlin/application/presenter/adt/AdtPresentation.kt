/*
 * Copyright (c) 2024. Andrea Giulianelli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package application.presenter.adt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.float
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import model.dt.DTUri
import model.event.CreateEvent
import model.event.DeleteEvent
import model.event.ShadowingEvent
import model.event.UpdateEvent
import model.semantics.DigitalTwinSemantics
import model.semantics.rdf.DTKnowledgeGraph
import java.net.URI

/**
 * It models the event received from Azure SignalR.
 * It contains:
 * - [dtId]: the Azure Digital Twin id
 * - [eventType]
 * - [eventDateTime]
 * - a list of the current [properties]
 * - a list of the current [relationships].
 */
@Serializable
data class SignalRDigitalTwinUpdate(
    val dtId: String,
    val eventType: SignalRDigitalTwinEventType,
    val eventDateTime: String,
    val properties: Map<String, JsonPrimitive> = mapOf(),
    val relationships: List<SignalRDigitalTwinRelationship> = listOf(),
)

/**
 * It models a relationship returned in the event from SignalR.
 * Each relationship has a [sourceId] associated with a [targetId] by a [relationshipName].
 * This adapter additionally extends support to [external] relationships.
 */
@Serializable
data class SignalRDigitalTwinRelationship(
    @SerialName("\$sourceId")
    val sourceId: String,
    @SerialName("\$relationshipName")
    val relationshipName: String,
    @SerialName("\$targetId")
    val targetId: String,
    val external: Boolean,
)

/** It models the Digital Twins event type. */
@Serializable
enum class SignalRDigitalTwinEventType {
    /** Create Digital Twin event type. */
    CREATE,

    /** Update Digital Twin event type. */
    UPDATE,

    /** Delete Digital Twin event type. */
    DELETE,
}

/**
 * It converts a [SignalRDigitalTwinUpdate] to a [ShadowingEvent]. The [ShadowingEvent] is built based on the provided
 * [dtUri] and [dtSemantics].
 */
fun SignalRDigitalTwinUpdate.toShadowingEvent(dtUri: DTUri, dtSemantics: DigitalTwinSemantics): ShadowingEvent =
    when (this.eventType) {
        SignalRDigitalTwinEventType.CREATE -> CreateEvent(this.dtId, this.extractDTKnowledgeGraph(dtUri, dtSemantics))
        SignalRDigitalTwinEventType.UPDATE -> UpdateEvent(this.extractDTKnowledgeGraph(dtUri, dtSemantics))
        SignalRDigitalTwinEventType.DELETE -> DeleteEvent(dtUri)
    }

/** It extracts the [DTKnowledgeGraph] from a [SignalRDigitalTwinUpdate] of a [dtUri], following its [dtSemantics]. */
fun SignalRDigitalTwinUpdate.extractDTKnowledgeGraph(
    dtUri: DTUri,
    dtSemantics: DigitalTwinSemantics,
): DTKnowledgeGraph =
    DTKnowledgeGraph(
        dtUri,
        this.properties.flatMap { (propertyName, jsonValue) ->
            dtSemantics.mapData(propertyName, jsonValue.toPrimitive()).orEmpty()
        } + this.relationships.flatMap { (_, relationshipName, targetID, _) ->
            dtSemantics.mapData(relationshipName, DTUri(URI.create(targetID))).orEmpty()
        },
    )

private fun JsonPrimitive.toPrimitive(): Any = when {
    this.isString -> this.content
    this.booleanOrNull != null -> this.boolean
    this.doubleOrNull != null -> this.double
    this.floatOrNull != null -> this.float
    this.intOrNull != null -> this.int
    else -> throw IllegalArgumentException("It is not a primitive")
}
