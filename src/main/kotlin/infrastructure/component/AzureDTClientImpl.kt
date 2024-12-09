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

import application.component.AzureDTClient
import application.presenter.adt.AzureDigitalTwinRelationship
import application.presenter.adt.AzureDigitalTwinState
import application.presenter.adt.SignalRDigitalTwinUpdate
import com.azure.digitaltwins.core.BasicDigitalTwin
import com.azure.digitaltwins.core.BasicRelationship
import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import com.azure.identity.DefaultAzureCredentialBuilder
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import configuration.AdapterConfiguration
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.coroutines.CoroutineContext
import com.azure.json.models.JsonObject as AzureJsonObject

/** Implementation of [AzureDTClient]. It takes the adapter [configuration]. */
class AzureDTClientImpl(
    configuration: AdapterConfiguration,
    private val context: CoroutineContext = Dispatchers.Default,
) : AzureDTClient {
    private val azureClient = DigitalTwinsClientBuilder()
        .credential(DefaultAzureCredentialBuilder().build())
        .endpoint(configuration.azureDTEndpoint.toString())
        .buildClient()
    private val _dtUpdates = MutableSharedFlow<SignalRDigitalTwinUpdate>()

    override val dtUpdates: Flow<SignalRDigitalTwinUpdate> = _dtUpdates.asSharedFlow()

    init {
        val signalRClient = HubConnectionBuilder.create(configuration.signalrNegotiationUrl.toString()).build()
        signalRClient.on(configuration.signalrTopicName, {
            CoroutineScope(context).launch {
                _dtUpdates.emit(Json.decodeFromString<SignalRDigitalTwinUpdate>(it))
            }
        }, String::class.java)
        signalRClient.persistentStart()
    }

    override fun getDTCurrentState(azureId: String): AzureDigitalTwinState? {
        val properties = azureClient.applySafeDigitalTwinOperation {
            getDigitalTwin(azureId, AzureJsonObject::class.java)
                ?.apply { listOf("\$dtId", "\$etag", "\$metadata").forEach { removeProperty(it) } }
                ?.let { Json.decodeFromString<JsonObject>(it.toJsonString()) }
                ?.mapValues { it.value.jsonPrimitive }
        }
        val relationships = azureClient.applySafeDigitalTwinOperation {
            listRelationships(azureId, BasicRelationship::class.java).mapNotNull {
                val targetId = it.targetId
                val relationshipName = it.name
                if (targetId != null && relationshipName != null) {
                    val targetDT = getDigitalTwin(targetId, BasicDigitalTwin::class.java)
                    val isRelationshipExternal = targetDT.metadata.modelId.equals(EXTERNAL_DT_MODEL)
                    AzureDigitalTwinRelationship(
                        relationshipName,
                        if (isRelationshipExternal) { targetDT.contents["uri"].toString() } else { targetId },
                        isRelationshipExternal,
                    )
                } else { null }
            }
        }

        return if (properties != null && relationships != null) {
            AzureDigitalTwinState(azureId, properties, relationships)
        } else {
            null
        }
    }

    override fun getDTModel(azureId: String): JsonObject? = azureClient.applySafeDigitalTwinOperation {
        getDigitalTwin(azureId, BasicDigitalTwin::class.java)
            ?.let {
                getModel(it.metadata.modelId)
            }?.let {
                Json.decodeFromString<JsonObject>(it.dtdlModel)
            }
    }

    private fun <R> DigitalTwinsClient.applySafeDigitalTwinOperation(operation: DigitalTwinsClient.() -> R): R? =
        try {
            operation()
        } catch (exception: ErrorResponseException) {
            logger.error { exception }
            null
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

    /** Companion object of the [AzureDTClient] class. */
    companion object {
        private const val EXTERNAL_DT_MODEL = "dtmi:io:github:wodt:ExternalDT;1"

        private val logger = KotlinLogging.logger {}
    }
}
