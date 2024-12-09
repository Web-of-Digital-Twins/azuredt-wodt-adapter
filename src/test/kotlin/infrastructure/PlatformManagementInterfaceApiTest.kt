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

import application.component.PlatformManagementInterface
import application.presenter.api.PlatformRegistration
import application.service.PlatformManagementInterfaceImpl
import infrastructure.testdouble.AdapterHttpClientTestDouble
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import model.dt.DTUri
import utils.TestingUtils.testApi
import java.net.URI

class PlatformManagementInterfaceApiTest : StringSpec({
    var platformManagementInterface: PlatformManagementInterface = PlatformManagementInterfaceImpl(
        AdapterHttpClientTestDouble(),
    )
    val platformUri = URI.create("http://platform-example.it")
    val dtUri = DTUri(URI.create("http://dt-uri.com"))

    beforeEach {
        platformManagementInterface = PlatformManagementInterfaceImpl(AdapterHttpClientTestDouble())
    }

    "it should be possible to notify the adapter that a platform has registered a dt" {
        testApi { _, _, _, platformManagementInterface ->
            platformManagementInterface[dtUri].contains(platformUri) shouldBe false
            val clientJson = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            val response = clientJson.post("/platform") {
                contentType(ContentType.Application.Json)
                setBody(PlatformRegistration(self = platformUri.toString(), dtUri.uri.toString()))
            }
            response shouldHaveStatus HttpStatusCode.OK
            platformManagementInterface[dtUri].contains(platformUri) shouldBe true
        }
    }

    "if the notification http request does not contain the required body then it must return 400" {
        testApi { _, _, _, platformManagementInterface ->
            val clientJson = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            val response = clientJson.post("/platform") {
                contentType(ContentType.Application.Json)
                setBody("[]")
            }
            response shouldHaveStatus HttpStatusCode.BadRequest
        }
    }
})
