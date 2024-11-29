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

import application.service.AzureDtIdDirectoryImpl
import application.service.Engine
import application.service.PlatformManagementInterfaceImpl
import configuration.ConfigurationLoader
import configuration.dsl.DslLoaderImpl
import infrastructure.component.AdapterHttpClientImpl
import infrastructure.component.AdapterWebServer
import infrastructure.component.AzureDTClientImpl
import infrastructure.component.JenaDtkgEngine
import infrastructure.component.SignalRShadowingAdapter
import infrastructure.component.WoTDtdManager
import kotlinx.coroutines.runBlocking

/**
 * Entry point of the Azure Digital Twins WoDT Adapter.
 */
fun main(): Unit = runBlocking {
    val configuration = ConfigurationLoader(DslLoaderImpl())
    val httpClient = AdapterHttpClientImpl()
    val azureDTClient = AzureDTClientImpl(configuration)
    val azureDtIdDirectory = AzureDtIdDirectoryImpl(configuration)
    val shadowingAdapter = SignalRShadowingAdapter(configuration, azureDTClient)
    val platformManagementInterface = PlatformManagementInterfaceImpl(httpClient)
    val dtdManager = WoTDtdManager(configuration, azureDTClient, platformManagementInterface)
    val dtkgEngine = JenaDtkgEngine(configuration)
    val webServer = AdapterWebServer(configuration, platformManagementInterface)

    val engine = Engine(
        configuration,
        azureDtIdDirectory,
        shadowingAdapter,
        dtkgEngine,
        dtdManager,
        platformManagementInterface,
        webServer,
    )
    engine.start()
}
