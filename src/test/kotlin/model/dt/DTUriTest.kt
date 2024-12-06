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

package model.dt

import configuration.Configuration
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import utils.TestingUtils.loadConfiguration
import java.net.URI

class DTUriTest : StringSpec({
    val adapterUrl = URI.create("http://localhost:5000/adapter")
    val configuration = object : Configuration by checkNotNull(loadConfiguration("simpleConfiguration.kts")) {
        override val exposedUrl: URI = adapterUrl
    }

    "it is possible to extract the DTUri of a DT to adapt from the configuration" {
        val existingDT = configuration.digitalTwinConfigurations.keys.first()
        DTUri.fromAzureID(existingDT, configuration) shouldNotBe null
    }

    "if it is possible to extract the DTUri, it should be correctly formed" {
        val azureId = "lampDT"
        val relativeUri = "lampDT"
        DTUri.fromAzureID(azureId, configuration) shouldBe
            DTUri(URI.create("$adapterUrl/$relativeUri"))
    }

    "if a DT should not be adapted, then it should not be possible to extract its DTUri" {
        DTUri.fromAzureID("not-existing-id", configuration) shouldBe null
    }
})
