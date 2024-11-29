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

import application.component.ShadowingAdapter
import application.presenter.adt.AzureDigitalTwinRelationship
import application.presenter.adt.AzureDigitalTwinState
import application.presenter.adt.SignalRDigitalTwinEventType
import application.presenter.adt.SignalRDigitalTwinRelationship
import application.presenter.adt.SignalRDigitalTwinUpdate
import application.presenter.adt.extractDTKnowledgeGraph
import application.presenter.adt.toShadowingEvent
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import configuration.Configuration
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.dt.DTUri
import model.event.CreateEvent
import model.event.ShadowingEvent
import java.time.LocalDateTime
import kotlin.coroutines.CoroutineContext

/**
 * This class implements a SignalR-based [ShadowingAdapter]. It is configured via its [configuration]
 * DT updates are mapped in DT snapshots by an Azure Function that are sent via SignalR to consumers.
 * An [azureDTClient] is necessary to initialize the shadowing.
 */
class SignalRShadowingAdapter(
    private val configuration: Configuration,
    private val azureDTClient: AzureDTClient,
    private val context: CoroutineContext = Dispatchers.IO,
) : ShadowingAdapter {
    private val _events = MutableSharedFlow<ShadowingEvent>()
    private val signalRClient = HubConnectionBuilder.create(configuration.signalrNegotiationUrl.toString()).build()

    override val events = _events.asSharedFlow()

    override suspend fun start() {
        CoroutineScope(context).launch { shadowExistingDts() }
        signalRClient.on(configuration.signalrTopicName, {
            Json.decodeFromString<SignalRDigitalTwinUpdate>(it)
                .filterEvent()
                ?.also { event ->
                    val dtId = DTUri.fromAzureID(event.dtId, configuration)
                    val dtSemantics = configuration.digitalTwinConfigurations[event.dtId]?.semantics
                    if (dtId != null && dtSemantics != null) {
                        CoroutineScope(context).launch {
                            _events.emit(event.toShadowingEvent(dtId, dtSemantics))
                        }
                    }
                }
        }, String::class.java)
        signalRClient.persistentStart()
    }

    private fun shadowExistingDts() {
        this.configuration.digitalTwinConfigurations.keys.forEach { azureDtId ->
            val dtUri = DTUri.fromAzureID(azureDtId, configuration)
            val semantics = configuration.digitalTwinConfigurations[azureDtId]?.semantics
            if (dtUri != null && semantics != null) {
                this.azureDTClient.getDTCurrentState(azureDtId)
                    ?.filterState()
                    ?.extractDTKnowledgeGraph(dtUri, semantics)?.let {
                        CoroutineScope(context).launch {
                            _events.emit(CreateEvent(azureDtId, it))
                        }
                    }
            }
        }
    }

    private fun AzureDigitalTwinState.filterState(): AzureDigitalTwinState? {
        fun AzureDigitalTwinState.toSignalRDigitalTwinUpdate() = SignalRDigitalTwinUpdate(
            this.dtId,
            SignalRDigitalTwinEventType.UPDATE,
            LocalDateTime.now().toString(),
            this.properties,
            this.relationships.map {
                SignalRDigitalTwinRelationship(this.dtId, it.relationshipName, it.targetId, it.external)
            },
        )

        fun SignalRDigitalTwinUpdate.toAzureDigitalTwinState() = AzureDigitalTwinState(
            this.dtId,
            this.properties,
            this.relationships.map { AzureDigitalTwinRelationship(it.relationshipName, it.targetId, it.external) },
        )
        return this.toSignalRDigitalTwinUpdate().filterEvent()?.toAzureDigitalTwinState()
    }

    private fun SignalRDigitalTwinUpdate.filterEvent() =
        this.takeIf { configuration.digitalTwinConfigurations.containsKey(it.dtId) }
            ?.let {
                it.copy(
                    relationships = it.relationships
                        .filter { relationship ->
                            relationship.external ||
                                configuration.digitalTwinConfigurations.containsKey(relationship.targetId)
                        }
                        .mapNotNull { relationship ->
                            if (relationship.external) {
                                relationship
                            } else {
                                DTUri.fromAzureID(relationship.targetId, configuration)?.let {
                                    relationship.copy(
                                        targetId = it.uri.toString(),
                                    )
                                }
                            }
                        },
                )
            }

    private fun HubConnection.persistentStart() {
        this.start().blockingAwait()
        logger.info { "Started" }
        this.onClosed {
            if (this.connectionState == HubConnectionState.DISCONNECTED) {
                this.persistentStart()
            }
        }
    }

    /** Companion object for [SignalRShadowingAdapter]. */
    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
