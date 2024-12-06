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

package infrastructure

import application.component.DtkgEngine
import infrastructure.component.JenaDtkgEngine
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import model.dt.DTUri
import model.semantics.rdf.DTKnowledgeGraph
import model.semantics.rdf.RdfBlankNode
import model.semantics.rdf.RdfLiteral
import model.semantics.rdf.RdfPredicateObjectPair
import model.semantics.rdf.RdfProperty
import model.semantics.rdf.RdfUriResource
import utils.TestingUtils.loadDTsConfiguration
import utils.TestingUtils.readResourceFile
import java.net.URI

class JenaDtkgEngineTest : StringSpec({
    val azureId = "lampDT"
    val dtUri = DTUri(URI.create("http://localhost/lampDT"))
    val notExistentDTUri = DTUri(URI.create("http://not-existent.it"))
    val dtKnowledgeGraph = DTKnowledgeGraph(
        dtUri,
        listOf(
            RdfPredicateObjectPair(
                RdfProperty(URI.create("http://ontology/prop")),
                RdfLiteral("example"),
            ),
            RdfPredicateObjectPair(
                RdfProperty(URI.create("http://ontology/prop2")),
                RdfBlankNode(
                    "blankNodeId",
                    listOf(
                        RdfPredicateObjectPair(
                            RdfProperty(URI.create("http://example.it/prop")),
                            RdfUriResource(URI.create("http://instance.it")),
                        ),
                    ),
                ),
            ),
        ),
    )
    val configuration = checkNotNull(loadDTsConfiguration("simpleConfiguration.kts"))
    var dtkg: DtkgEngine = JenaDtkgEngine(configuration)

    beforeEach {
        dtkg = JenaDtkgEngine(configuration)
        dtkg.updateSingleDT(azureId, dtKnowledgeGraph)
    }

    "it should not be possible to obtain a DTKG of a not-existent DT" {
        dtkg[notExistentDTUri] shouldBe null
    }

    "an update of an existing uri should be performed correctly" {
        dtkg.updateSingleDT(azureId, dtKnowledgeGraph)
        dtkg[dtUri] shouldNotBe null
    }

    "a dtkg should return valid Turtle" {
        dtkg[dtUri]?.dtkg shouldBe readResourceFile("exampleDTKG.ttl")
    }

    "it should not be possible to delete a not existing DTKG" {
        dtkg.deleteSingleDT(notExistentDTUri) shouldBe false
    }

    "it should be possible to delete an existing DTKG" {
        dtkg.deleteSingleDT(dtUri) shouldBe true
        dtkg[dtUri] shouldBe null
    }
})
