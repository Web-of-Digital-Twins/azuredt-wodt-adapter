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

package application.presenter.adt

import application.presenter.adt.DtdlPresentation.toWoTThingDescription
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import model.dt.DTUri
import utils.TestingUtils.loadDTsConfiguration
import utils.TestingUtils.readResourceFile
import java.net.URI

class DtdlPresentationTest : StringSpec({
    val dtUri = DTUri(URI.create("http://localhost:5000/lampDT"))
    val azureDTId = "lampDT"
    val platforms = listOf("http://localhost:4000", "http://localhost:6000").map { URI.create(it) }
    val configuration =
        checkNotNull(loadDTsConfiguration("simpleConfiguration.kts").digitalTwinConfigurations[azureDTId])
    val lampDtdl = readResourceFile("./lampDtdl.json").let {
        Json.decodeFromString<JsonObject>(it)
    }

    "it should be possible to correctly translate a dtdl model to a WoT Thing Description" {
        checkNotNull(
            lampDtdl.toWoTThingDescription(dtUri, platforms, configuration)?.toJsonString(),
        )
            .replace("\n", "")
            .replace(" ", "") shouldBe readResourceFile("lampTD.json").replace("\n", "").replace(" ", "")
    }
})
