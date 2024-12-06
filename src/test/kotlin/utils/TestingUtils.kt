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

package utils

import application.component.DtdManager
import application.component.DtkgEngine
import application.service.AdapterDirectory
import application.service.AdapterDirectoryService
import configuration.AdapterDTsConfiguration
import configuration.Configuration
import configuration.DigitalTwinConfiguration
import configuration.dsl.AdapterDTsConfigurationDsl
import configuration.dsl.DslLoaderImpl
import infrastructure.component.JenaDtkgEngine
import infrastructure.component.WoTDtdManager
import infrastructure.component.api.wodtDigitalTwinInterfaceApi
import infrastructure.testdouble.AzureDTClientTestDouble
import infrastructure.testdouble.PlatformManagementInterfaceTestDouble
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.ktor.server.websocket.WebSockets
import java.net.URI

/** Module that wraps some testing utilities. */
object TestingUtils {
    /** Read a file content from resource. */
    fun readResourceFile(fileName: String) =
        checkNotNull(this::class.java.getClassLoader().getResource(fileName)?.readText(Charsets.UTF_8))

    fun loadDTsConfiguration(fileName: String) = DslLoaderImpl().load(
        this::class.java.classLoader.getResource(fileName)?.path.orEmpty(),
        AdapterDTsConfigurationDsl.DSL_IMPORTS,
    ) as AdapterDTsConfiguration

    fun loadConfiguration(dtsConfigurationFileName: String) = (
        DslLoaderImpl().load(
            this::class.java.classLoader.getResource(dtsConfigurationFileName)?.path.orEmpty(),
            AdapterDTsConfigurationDsl.DSL_IMPORTS,
        ) as AdapterDTsConfiguration
        ).let {
        object : Configuration {
            override val exposedPort: Int = 5000
            override val exposedUrl: URI = URI.create("http://localhost:5000")
            override val azureClientID: String = ""
            override val azureTenantID: String = ""
            override val azureClientSecret: String = ""
            override val azureDTEndpoint: URI = URI.create("")
            override val signalrNegotiationUrl: URI = URI.create("")
            override val signalrTopicName: String = ""
            override val digitalTwinConfigurations: Map<String, DigitalTwinConfiguration> = it.digitalTwinConfigurations
        }
    }

    fun testApi(
        tests: suspend ApplicationTestBuilder.(
            adapterDirectory: AdapterDirectory,
            dtkgEngine: DtkgEngine,
            dtdManager: DtdManager,
        ) -> Unit,
    ) {
        val configuration = checkNotNull(loadConfiguration("simpleConfiguration.kts"))
        val adapterDirectory = AdapterDirectoryService(configuration)
        val dtkgEngine = JenaDtkgEngine(configuration)
        val dtdManager = WoTDtdManager(
            configuration,
            AzureDTClientTestDouble(),
            PlatformManagementInterfaceTestDouble(),
        )

        testApplication {
            install(WebSockets)
            install(ContentNegotiation) {
                json()
            }
            application {
                wodtDigitalTwinInterfaceApi(adapterDirectory, dtkgEngine, dtdManager)
            }
            tests(adapterDirectory, dtkgEngine, dtdManager)
        }
    }
}
