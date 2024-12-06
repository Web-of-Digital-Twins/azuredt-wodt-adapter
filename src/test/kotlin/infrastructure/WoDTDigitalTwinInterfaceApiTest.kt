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

import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import model.dt.DTUri
import model.ontology.WoDTVocabulary
import model.semantics.rdf.DTKnowledgeGraph
import model.semantics.rdf.RdfLiteral
import model.semantics.rdf.RdfPredicateObjectPair
import model.semantics.rdf.RdfProperty
import utils.TestingUtils.testApi
import java.net.URI

class WoDTDigitalTwinInterfaceApiTest : StringSpec({
    val azureId = "lampDT"
    val dtUri = DTUri(URI.create("http://localhost:5000/lampDT"))
    val dtKnowledgeGraph = DTKnowledgeGraph(
        dtUri,
        listOf(
            RdfPredicateObjectPair(
                RdfProperty(URI.create("http://ontology/prop")),
                RdfLiteral("example"),
            ),
        ),
    )

    "an HTTP GET request on a DT URI should return 303 and link to the DTKG" {
        testApi { adapterDirectory, _, _ ->
            adapterDirectory.addDT(azureId, dtUri)
            val clientNoRedirect = createClient {
                followRedirects = false
            }
            val response = clientNoRedirect.get("/lampDT")
            response shouldHaveStatus HttpStatusCode.SeeOther
            response.headers[HttpHeaders.Location] shouldBe "${dtUri.uri}/dtkg"
        }
    }

    "an HTTP GET request on a not exposed DT URI should return 404" {
        testApi { _, _, _ ->
            val response = client.get("/lampDT")
            response shouldHaveStatus HttpStatusCode.NotFound
        }
    }

    "an HTTP GET request on a not existent DTKG should return 404" {
        testApi { _, _, _ ->
            val response = client.get("/lampDT/dtkg")
            response shouldHaveStatus HttpStatusCode.NotFound
        }
    }

    "an HTTP GET request on an existent DTKG should return the DTKG" {
        testApi { directory, dtkgEngine, _ ->
            directory.addDT(azureId, dtUri)
            dtkgEngine.updateSingleDT(azureId, dtKnowledgeGraph)
            val response = client.get("/lampDT/dtkg")
            response.status shouldBe HttpStatusCode.OK
            response.contentType()?.toString() shouldBe "text/turtle"
            response.headers[HttpHeaders.Link] shouldBe "<dtd>; rel=\"${WoDTVocabulary.DTD}\""
            response.bodyAsText() shouldBe dtkgEngine[dtUri]?.dtkg
        }
    }

    "an HTTP GET request on a not existent DTD should return 404" {
        testApi { _, _, _ ->
            val response = client.get("/lampDT/dtd")
            response shouldHaveStatus HttpStatusCode.NotFound
        }
    }

    "an HTTP GET request on an existent DTD should return the DTD" {
        testApi { directory, _, dtdManager ->
            directory.addDT(azureId, dtUri)
            val response = client.get("/lampDT/dtd")
            response shouldHaveStatus HttpStatusCode.OK
            response.bodyAsText() shouldBe dtdManager[azureId]?.toJsonString()
        }
    }
})
