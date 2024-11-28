import application.service.AzureDtIdDirectoryImpl
import application.service.Engine
import configuration.ConfigurationLoader
import configuration.dsl.DslLoaderImpl
import infrastructure.component.AzureDTClientImpl
import infrastructure.component.SignalRShadowingAdapter
import kotlinx.coroutines.runBlocking

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

/**
 * Entry point of the Azure Digital Twins WoDT Adapter.
 */
fun main(): Unit = runBlocking {
    val configuration = ConfigurationLoader(DslLoaderImpl())
    val azureDTClient = AzureDTClientImpl(configuration)
    val azureDtIdDirectory = AzureDtIdDirectoryImpl()
    val shadowingAdapter = SignalRShadowingAdapter(configuration, azureDTClient)

    val engine = Engine(azureDtIdDirectory, shadowingAdapter)
    engine.start()
}
