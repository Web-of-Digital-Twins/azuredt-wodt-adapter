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

package configuration.dsl

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.dt.DTVersion
import model.semantics.rdf.RdfBlankNode
import model.semantics.rdf.RdfIndividual
import model.semantics.rdf.RdfLiteral
import model.semantics.rdf.RdfPredicateObjectPair
import model.semantics.rdf.RdfProperty
import java.net.URI

class AdapterDTsConfigurationDslTest : StringSpec({
    "a simple complete configuration must be compiled" {
        configuration {
            dt("lampDT") {
                relativeUri = uri("/lampDT")
                version = DTVersion(1, 0, 0)
                physicalAssetID = "lampPA"
                platformsToRegister = listOf(uri("http://localhost:5000"), uri("http://platform.it"))
                semantics {
                    classes {
                        -"https://saref.etsi.org/core/Actuator"
                        -"https://w3id.org/rec/Lamp"
                    }
                    domainTags {
                        -("luminosity" to uri("https://purl.org/onto/LuminosityFlux"))
                        -("isInRoom" to uri("https://brickschema.org/schema/Brick#hasLocation"))
                    }
                    propertyMapping {
                        -(
                            "luminosity" to { value: Any ->
                                listOf(
                                    RdfPredicateObjectPair(
                                        RdfProperty(uri("https://saref.etsi.org/core/hasProperty")),
                                        RdfIndividual(uri("\"https://purl.org/onto/LuminosityFlux\"")),
                                    ),
                                    RdfPredicateObjectPair(
                                        RdfProperty(uri("\"https://saref.etsi.org/core/hasPropertyValue\"")),
                                        RdfBlankNode(
                                            "luminosityValue",
                                            listOf(
                                                RdfPredicateObjectPair(
                                                    RdfProperty(uri("https://saref.etsi.org/core/hasValue")),
                                                    RdfLiteral<Double>(value as Double),
                                                ),
                                                RdfPredicateObjectPair(
                                                    RdfProperty(uri("https://saref.etsi.org/core/isValueOfProperty")),
                                                    RdfIndividual(uri("https://purl.org/onto/LuminosityFlux")),
                                                ),
                                            ),
                                        ),
                                    ),
                                )
                            }
                            )
                    }
                    relationshipMapping {
                        -(
                            "isInRoom" to { target: URI ->
                                listOf(
                                    RdfPredicateObjectPair(
                                        RdfProperty(uri("https://brickschema.org/schema/Brick#hasLocation")),
                                        RdfIndividual(uri(target.toString())),
                                    ),
                                )
                            }
                            )
                    }
                }
            }
        }.digitalTwinConfigurations.size shouldBe 1
    }
})
