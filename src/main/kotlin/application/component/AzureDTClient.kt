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

package application.component

import application.presenter.adt.AzureDigitalTwinState
import application.presenter.adt.SignalRDigitalTwinUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject

/**
 * This interface models the client to perform operations on Azure Digital Twins
 * and the services on the designed pipeline.
 */
interface AzureDTClient {
    /** Obtain the Digital Twins Snapshot events from SignalR. */
    val dtUpdates: Flow<SignalRDigitalTwinUpdate>

    /** Get the current state of the Digital Twin with the provided [azureId]. */
    fun getDTCurrentState(azureId: String): AzureDigitalTwinState?

    /** Get the DTDL model of the Digital Twin in ADT with the provided [azureId]. */
    fun getDTModel(azureId: String): JsonObject?
}
