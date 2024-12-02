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

package application.service

import application.component.DtdManager
import application.component.DtkgEngine
import application.component.PlatformManagementInterface
import application.component.ShadowingAdapter
import application.component.WebServer
import configuration.Configuration
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import model.dt.DTUri
import model.event.CreateEvent
import model.event.DeleteEvent
import model.event.UpdateEvent

/**
 * The Engine that runs the ADT WoDT Adapter.
 * It takes the:
 * - [adapterDirectory]
 * - [shadowingAdapter]
 */
class Engine(
    private val configuration: Configuration,
    private val adapterDirectory: AdapterDirectory,
    private val shadowingAdapter: ShadowingAdapter,
    private val dtkgEngine: DtkgEngine,
    private val dtdManager: DtdManager,
    private val platformManagementInterface: PlatformManagementInterface,
    private val webServer: WebServer,
) {
    /**
     * Start the engine.
     */
    suspend fun start() = coroutineScope {
        launch {
            shadowingAdapter.events.collect {
                logger.info { it }
                when (it) {
                    is CreateEvent -> {
                        adapterDirectory.addDT(it.azureDtId, it.dtKnowledgeGraph.dtUri)
                        launch {
                            registerDigitalTwinToDefaultPlatforms(
                                it.azureDtId,
                                it.dtKnowledgeGraph.dtUri,
                                dtdManager,
                                platformManagementInterface,
                            )
                        }
                        dtkgEngine.updateSingleDT(it.azureDtId, it.dtKnowledgeGraph)
                    }
                    is DeleteEvent -> {
                        adapterDirectory.removeDT(it.dtUri)
                        launch { platformManagementInterface.signalDTDeletion(it.dtUri) }
                        dtkgEngine.deleteSingleDT(it.dtUri)
                    }
                    is UpdateEvent -> {
                        dtkgEngine.updateSingleDT(it.azureDtId, it.dtKnowledgeGraph)
                    }
                }
            }
        }
        launch {
            logger.info { "Starting to extend the shadowing process..." }
            shadowingAdapter.start()
        }
        webServer.start()
    }

    private suspend fun registerDigitalTwinToDefaultPlatforms(
        azureDtId: String,
        dtUri: DTUri,
        dtdManager: DtdManager,
        platformManagementInterface: PlatformManagementInterface,
    ) {
        coroutineScope {
            dtdManager[azureDtId]?.also { dtd ->
                configuration.digitalTwinConfigurations[azureDtId]
                    ?.platformsToRegister
                    ?.forEach { platform ->
                        launch {
                            platformManagementInterface.requestPlatformRegistration(
                                platform,
                                dtUri,
                                dtd.toJsonString(),
                            )
                        }
                    }
            }
        }
    }

    /** Companion object of the [Engine] class. */
    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
