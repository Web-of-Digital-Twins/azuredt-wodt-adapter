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
}