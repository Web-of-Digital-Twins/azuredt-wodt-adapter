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

package infrastructure.component

import application.presenter.adt.AzureDigitalTwinRelationship
import application.presenter.adt.AzureDigitalTwinState
import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicRelationship
import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import com.azure.identity.DefaultAzureCredentialBuilder
import configuration.AdapterConfiguration
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.azure.json.models.JsonObject as AzureJsonObject

/** This interface models the client to perform operations on Azure Digital Twins. */
interface AzureDTClient {
    /** Get the current state of the Digital Twin with the provided [azureId]. */
    fun getDTCurrentState(azureId: String): AzureDigitalTwinState?

    /** Get the DTDL model of the Digital Twin in ADT with the provided [azureId]. */
    fun getDTModel(azureId: String): JsonObject?
}

/** Implementation of [AzureDTClient]. It takes the adapter [configuration]. */
class AzureDTClientImpl(configuration: AdapterConfiguration) : AzureDTClient {
    private val azureClient = DigitalTwinsClientBuilder()
        .credential(DefaultAzureCredentialBuilder().build())
        .endpoint(configuration.azureDTEndpoint.toString())
        .buildClient()

    override fun getDTCurrentState(azureId: String): AzureDigitalTwinState? {
        val properties = azureClient.applySafeDigitalTwinOperation {
            getDigitalTwin(azureId, AzureJsonObject::class.java)
                ?.apply { listOf("\$dtId", "\$etag", "\$metadata").forEach { removeProperty(it) } }
                ?.let { Json.decodeFromString<JsonObject>(it.toJsonString()) }
                ?.mapValues { it.value.jsonPrimitive }
        }
        val relationships = azureClient.applySafeDigitalTwinOperation {
            listRelationships(azureId, BasicRelationship::class.java).mapNotNull {
                val targetId = it.targetId
                val relationshipName = it.name
                if (targetId != null && relationshipName != null) {
                    val targetDT = getDigitalTwin(targetId, BasicDigitalTwin::class.java)
                    val isRelationshipExternal = targetDT.metadata.modelId.equals(EXTERNAL_DT_MODEL)
                    AzureDigitalTwinRelationship(
                        relationshipName,
                        if (isRelationshipExternal) { targetDT.contents["uri"].toString() } else { targetId },
                        isRelationshipExternal,
                    )
                } else { null }
            }
        }

        return if (properties != null && relationships != null) {
            AzureDigitalTwinState(azureId, properties, relationships)
        } else {
            null
        }
    }

    override fun getDTModel(azureId: String): JsonObject? = azureClient.applySafeDigitalTwinOperation {
        getDigitalTwin(azureId, BasicDigitalTwin::class.java)
            ?.let {
                getModel(it.metadata.modelId)
            }?.let {
                Json.decodeFromString<JsonObject>(it.dtdlModel)
            }
    }

    private fun <R> DigitalTwinsClient.applySafeDigitalTwinOperation(operation: DigitalTwinsClient.() -> R): R? =
        try {
            operation()
        } catch (exception: ErrorResponseException) {
            logger.error { exception }
            null
        }

    /** Companion object of the [AzureDTClient] class. */
    companion object {
        private const val EXTERNAL_DT_MODEL = "dtmi:io:github:wodt:ExternalDT;1"

        private val logger = KotlinLogging.logger {}
    }
}
