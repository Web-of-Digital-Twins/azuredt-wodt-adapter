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

package infrastructure.testdouble

import application.presenter.adt.AzureDigitalTwinRelationship
import application.presenter.adt.AzureDigitalTwinState
import infrastructure.component.AzureDTClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import utils.TestingUtils.readResourceFile

/** A simple [AzureDTClient] test double. */
class AzureDTClientTestDouble : AzureDTClient {
    override fun getDTCurrentState(azureId: String): AzureDigitalTwinState? =
        if (azureId == "lampDT") {
            AzureDigitalTwinState(
                dtId = "lampDT",
                properties = mapOf("luminosity" to JsonPrimitive(100)),
                relationships = listOf(
                    AzureDigitalTwinRelationship(
                        relationshipName = "isInRoom",
                        targetId = "http://external-dt.com",
                        external = true,
                    ),
                    AzureDigitalTwinRelationship(
                        relationshipName = "isInRoom",
                        targetId = "internal-room",
                        external = false,
                    ),
                ),
            )
        } else {
            null
        }

    override fun getDTModel(azureId: String): JsonObject? =
        if (azureId == "lampDT") {
            readResourceFile("./lampDtdl.json").let {
                Json.decodeFromString<JsonObject>(it)
            }
        } else {
            null
        }
}
