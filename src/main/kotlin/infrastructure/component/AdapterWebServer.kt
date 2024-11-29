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

import application.component.PlatformManagementInterfaceNotifier
import application.component.WebServer
import configuration.AdapterConfiguration
import infrastructure.component.api.platformManagementInterfaceApi
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.websocket.WebSockets

/** This class implements the Adapter [WebServer] that manages all the exposed APIs of the Adapter. */
class AdapterWebServer(
    private val configuration: AdapterConfiguration,
    private val platformManagementInterfaceNotifier: PlatformManagementInterfaceNotifier,
) : WebServer {
    override fun start() {
        embeddedServer(Netty, port = configuration.exposedPort) {
            install(WebSockets)
            install(ContentNegotiation) {
                json()
            }
            dispatcher(this)
        }.start(wait = false)
    }

    private fun dispatcher(app: Application) {
        with(app) {
            platformManagementInterfaceApi(platformManagementInterfaceNotifier)
        }
    }
}
