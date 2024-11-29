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

import configuration.Configuration
import configuration.DigitalTwinConfiguration
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.dt.DTUri
import java.net.URI

class AzureDtIdDirectoryServiceTest : StringSpec({
    val configuration = object : Configuration {
        override val exposedPort: Int = 5000
        override val exposedUrl: URI = URI.create("http://localhost:5000/platform")
        override val azureClientID: String = ""
        override val azureTenantID: String = ""
        override val azureClientSecret: String = ""
        override val azureDTEndpoint: URI = URI.create("")
        override val signalrNegotiationUrl: URI = URI.create("")
        override val signalrTopicName: String = ""
        override val digitalTwinConfigurations: Map<String, DigitalTwinConfiguration> = mapOf()
    }
    val azureDtId = "lampDT"
    val dtUri = DTUri(URI.create("http://localhost:5000/platform/somePath/lampDT"))
    val relativeDtUri = URI.create("somePath/lampDT")

    "it should be possible to retrieve the azure id of a added dt uri" {
        val directory = AzureDtIdDirectoryService(configuration)
        directory.addDT(azureDtId, dtUri)
        directory[dtUri] shouldBe azureDtId
    }

    "it should not be possible to retrieve the azure if of a not-existent dt uri" {
        val directory = AzureDtIdDirectoryService(configuration)
        directory[dtUri] shouldBe null
    }

    "it should be possible to retrieve the azure id of a added dt uri, passing the relative dt uri" {
        val directory = AzureDtIdDirectoryService(configuration)
        directory.addDT(azureDtId, dtUri)
        directory.getFromRelative(relativeDtUri) shouldBe azureDtId
    }
})
