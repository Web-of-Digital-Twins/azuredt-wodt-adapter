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

import application.component.DtdManager
import application.component.PlatformManagementInterfaceReader
import application.presenter.adt.DtdlPresentation.toWoTThingDescription
import configuration.Configuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import model.dt.DTUri
import org.eclipse.ditto.wot.model.ThingDescription
import kotlin.coroutines.CoroutineContext

/**
 * A Digital Twin Description (DTD) Manager implementation that convert Azure Digital Twins DTDL
 * models to WoT Thing Descriptions.
 * It exploits the input [configuration] and its tasks will be executed within the provided [context].
 * The [azureDTClient] and [azureDtIdDirectory] will be exploited to retrieve the DTDL models.
 * Instead, the [platformManagementInterfaceReader] will be exploited to retrieve the platform to which
 * the DT is registered.
 */
class WoTDtdManager(
    private val configuration: Configuration,
    private val azureDTClient: AzureDTClient,
    private val platformManagementInterfaceReader: PlatformManagementInterfaceReader,
    private val context: CoroutineContext = Dispatchers.Default,
) : DtdManager {
    override suspend operator fun get(azureDtId: String): Deferred<ThingDescription?> =
        CoroutineScope(context).async {
            DTUri.fromAzureID(azureDtId, configuration)?.let { dtUri ->
                configuration.digitalTwinConfigurations[azureDtId]?.let { dtConfiguration ->
                    azureDTClient.getDTModel(azureDtId)
                        ?.toWoTThingDescription(
                            dtUri,
                            platformManagementInterfaceReader[dtUri],
                            dtConfiguration,
                        )
                }
            }
        }
}
