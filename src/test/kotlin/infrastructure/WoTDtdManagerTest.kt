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

package infrastructure

import infrastructure.component.WoTDtdManager
import infrastructure.testdouble.AzureDTClientTestDouble
import infrastructure.testdouble.PlatformManagementInterfaceTestDouble
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import model.dt.DTUri
import utils.TestingUtils.loadConfiguration
import utils.TestingUtils.readResourceFile
import java.net.URI

class WoTDtdManagerTest : StringSpec({
    val configuration = checkNotNull(loadConfiguration("simpleConfiguration.kts"))
    val platformManagementInterface = PlatformManagementInterfaceTestDouble()
    val azureDtId = "lampDT"
    val wotDtdManager = WoTDtdManager(
        configuration,
        AzureDTClientTestDouble(),
        platformManagementInterface,
    )

    "it should be possible to load the DTD of an Azure Digital Twin for which a configuration is available" {
        listOf("http://localhost:4000", "http://localhost:6000").map { URI.create(it) }.forEach {
            platformManagementInterface
                .requestPlatformRegistration(checkNotNull(DTUri.fromAzureID(azureDtId, configuration)), it)
        }
        runTest {
            wotDtdManager[azureDtId]?.toJsonString() shouldBe
                readResourceFile("lampTD.json").replace("\n", "").replace(" ", "")
        }
    }
})
