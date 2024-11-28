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

import configuration.dsl.uri
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import model.dt.DTUri
import model.event.DeleteEvent
import model.event.UpdateEvent
import model.semantics.rdf.DTKnowledgeGraph
import model.semantics.rdf.RdfBlankNode
import model.semantics.rdf.RdfIndividual
import model.semantics.rdf.RdfLiteral
import model.semantics.rdf.RdfPredicateObjectPair
import model.semantics.rdf.RdfProperty
import utils.TestingUtils.loadDTsConfiguration
import utils.TestingUtils.readResourceFile

class AdtPresentationTest : StringSpec({
    val dtUri = DTUri(uri("http://uri.com"))

    val signalRDTUpdate = SignalRDigitalTwinUpdate(
        dtId = "lampDT",
        eventType = SignalRDigitalTwinEventType.UPDATE,
        eventDateTime = "2024-01-01T10:00:00",
        mapOf(
            "luminosity" to JsonPrimitive(100),
        ),
        listOf(
            SignalRDigitalTwinRelationship(
                sourceId = "lampDT",
                relationshipName = "isInRoom",
                targetId = "http://example.com",
                external = true,
            ),
        ),
    )

    val signalRDTDelete = SignalRDigitalTwinUpdate(
        dtId = "lampDT",
        eventType = SignalRDigitalTwinEventType.DELETE,
        eventDateTime = "2024-01-01T10:00:00",
    )

    val azureDTState = AzureDigitalTwinState(
        dtId = "lampDT",
        mapOf(
            "luminosity" to JsonPrimitive(100),
        ),
        listOf(
            AzureDigitalTwinRelationship(
                relationshipName = "isInRoom",
                targetId = "http://example.com",
                external = true,
            ),
        ),
    )

    val extractedDTKnowledgeGraph = DTKnowledgeGraph(
        dtUri = dtUri,
        listOf(
            RdfPredicateObjectPair(
                RdfProperty(uri("https://saref.etsi.org/core/hasProperty")),
                RdfIndividual(uri("https://purl.org/onto/LuminosityFlux")),
            ),
            RdfPredicateObjectPair(
                RdfProperty(uri("https://saref.etsi.org/core/hasPropertyValue")),
                RdfBlankNode(
                    "luminosityValue",
                    listOf(
                        RdfPredicateObjectPair(
                            RdfProperty(uri("https://saref.etsi.org/core/hasValue")),
                            RdfLiteral<Double>(100.0),
                        ),
                        RdfPredicateObjectPair(
                            RdfProperty(uri("https://saref.etsi.org/core/isValueOfProperty")),
                            RdfIndividual(uri("https://purl.org/onto/LuminosityFlux")),
                        ),
                    ),
                ),
            ),
            RdfPredicateObjectPair(
                RdfProperty(uri("https://brickschema.org/schema/Brick#hasLocation")),
                RdfIndividual(uri("http://example.com")),
            ),
        ),
    )

    val dtsConfiguration = loadDTsConfiguration("simpleConfiguration.kts")

    "A SignalR Digital Twin Create event should be deserialized correctly" {
        readResourceFile("./signalRCreate.json").let {
            shouldNotThrow<Exception> {
                val event = Json.decodeFromString<SignalRDigitalTwinUpdate>(it)
                event shouldNotBe null
                event.eventType shouldBe SignalRDigitalTwinEventType.CREATE
            }
        }
    }

    "A SignalR Digital Twin Update event should be deserialized correctly" {
        readResourceFile("./signalRUpdate.json").let {
            shouldNotThrow<Exception> {
                val event = Json.decodeFromString<SignalRDigitalTwinUpdate>(it)
                event shouldNotBe null
                event.eventType shouldBe SignalRDigitalTwinEventType.UPDATE
                event.properties.size shouldNotBe 0
                event.relationships.size shouldNotBe 0
            }
        }
    }

    "A SignalR Digital Twin Delete event should be deserialized correctly" {
        readResourceFile("./signalRDelete.json").let {
            shouldNotThrow<Exception> {
                val event = Json.decodeFromString<SignalRDigitalTwinUpdate>(it)
                event shouldNotBe null
                event.eventType shouldBe SignalRDigitalTwinEventType.DELETE
            }
        }
    }

    "It should be possible to extract the DT Knowledge Graph from a SignalR Digital Twin Update" {
        signalRDTUpdate.extractDTKnowledgeGraph(
            dtUri,
            checkNotNull(dtsConfiguration.digitalTwinConfigurations["lampDT"]?.semantics) { "check configuration" },
        ) shouldBe extractedDTKnowledgeGraph
    }

    "A Digital Twin Delete event should be converted in an empty DT Knowledge Graph" {
        signalRDTDelete.extractDTKnowledgeGraph(
            dtUri,
            checkNotNull(dtsConfiguration.digitalTwinConfigurations["lampDT"]?.semantics) { "check configuration" },
        ) shouldBe DTKnowledgeGraph(dtUri, listOf())
    }

    "A Digital Twin Update event should be converted in a Update Shadowing event" {
        val semantics = checkNotNull(dtsConfiguration.digitalTwinConfigurations["lampDT"]?.semantics) {
            "check configuration"
        }

        signalRDTUpdate.toShadowingEvent(dtUri, semantics) shouldBe
            UpdateEvent(signalRDTUpdate.extractDTKnowledgeGraph(dtUri, semantics))
    }

    "A Digital Twin Delete event should be converted in a Delete Shadowing event" {
        val semantics = checkNotNull(dtsConfiguration.digitalTwinConfigurations["lampDT"]?.semantics) {
            "check configuration"
        }

        signalRDTDelete.toShadowingEvent(dtUri, semantics) shouldBe DeleteEvent(dtUri)
    }

    "It should be possible to extract the DT Knowledge Graph from a Azure Digital Twin current state" {
        azureDTState.extractDTKnowledgeGraph(
            dtUri,
            checkNotNull(dtsConfiguration.digitalTwinConfigurations["lampDT"]?.semantics) { "check configuration" },
        ) shouldBe extractedDTKnowledgeGraph
    }
})
