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

package infrastructure.component.api

import application.component.DtdManager
import application.component.DtkgEngineReader
import application.service.AdapterDirectoryReader
import application.service.AzureDtIdDirectoryReader
import application.service.DTUriDirectoryReader
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.send
import kotlinx.coroutines.flow.filter
import model.ontology.WoDTVocabulary
import java.net.URI
import kotlin.text.Regex

/** The WoDT Digital Twin Interface API available to Consumers. */
fun Application.wodtDigitalTwinInterfaceApi(
    adapterDirectoryReader: AdapterDirectoryReader,
    dtkgEngine: DtkgEngineReader,
    dtdManager: DtdManager,
) {
    routing {
        getDigitalTwin(adapterDirectoryReader)
        getDigitalTwinDtkg(dtkgEngine, adapterDirectoryReader)
        getDigitalTwinDtkgEvents(dtkgEngine, adapterDirectoryReader)
        getDigitalTwinDtd(dtdManager, adapterDirectoryReader)
    }
}

private fun Route.getDigitalTwin(adapterDirectoryReader: DTUriDirectoryReader) =
    get(Regex("""(?<dtRelativeUri>.+)(?<!/dtkg|/dtd)""")) {
        call.parameters.getAll("dtRelativeUri")?.let {
            adapterDirectoryReader.getDTUriFromRelative(URI.create(joinParametersInRelativeUri(it)))
        }?.let {
            call.response.headers.append(HttpHeaders.Location, "${it.uri}/dtkg")
            call.respond(HttpStatusCode.SeeOther)
        } ?: call.respond(HttpStatusCode.NotFound)
    }

private fun Route.getDigitalTwinDtkg(dtkgEngine: DtkgEngineReader, adapterDirectoryReader: DTUriDirectoryReader) =
    get(Regex("""(?<dtRelativeUri>.+)/dtkg""")) {
        call.parameters.getAll("dtRelativeUri")?.let {
            adapterDirectoryReader.getDTUriFromRelative(URI.create(joinParametersInRelativeUri(it)))
        }?.let {
            dtkgEngine[it]
        }?.apply {
            call.response.status(if (this.dtkg.isEmpty()) { HttpStatusCode.NoContent } else { HttpStatusCode.OK })
            call.response.headers.append(HttpHeaders.ContentType, "text/turtle")
            call.response.headers.append(
                HttpHeaders.Link,
                "<dtd>; rel=\"${WoDTVocabulary.DTD}\"",
            )
            call.respond(this.dtkg)
        } ?: call.respond(HttpStatusCode.NotFound)
    }

private fun Route.getDigitalTwinDtkgEvents(dtkgEngine: DtkgEngineReader, adapterDirectoryReader: DTUriDirectoryReader) =
    route(Regex("""(?<dtRelativeUri>.+)/dtkg""")) {
        webSocket {
            call.parameters.getAll("dtRelativeUri")?.let {
                adapterDirectoryReader.getDTUriFromRelative(URI.create(joinParametersInRelativeUri(it)))
            }?.let { interestedDtUri ->
                send(dtkgEngine[interestedDtUri]?.dtkg.orEmpty())
                dtkgEngine.dtkgs
                    .filter { it.dtUri == interestedDtUri }
                    .collect { send(it.dtkg) }
            }
        }
    }

private fun Route.getDigitalTwinDtd(dtdManager: DtdManager, adapterDirectoryReader: AzureDtIdDirectoryReader) =
    get(Regex("""(?<dtRelativeUri>.+)/dtd""")) {
        call.parameters.getAll("dtRelativeUri")?.let {
            adapterDirectoryReader.getFromRelative(URI.create(joinParametersInRelativeUri(it)))
        }?.let {
            dtdManager[it]
        }?.apply {
            call.response.status(HttpStatusCode.OK)
            call.response.headers.append(HttpHeaders.ContentType, "application/td+json")
            call.respond(this.toJsonString())
        } ?: call.respond(HttpStatusCode.NotFound)
    }

private fun joinParametersInRelativeUri(parameters: List<String>) = parameters.joinToString("/", "", "")
