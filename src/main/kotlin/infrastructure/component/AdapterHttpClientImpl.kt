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

import application.component.AdapterHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import java.net.URI

/** Simple implementation of the [AdapterHttpClient] interface. */
class AdapterHttpClientImpl : AdapterHttpClient {
    private val httpClient = HttpClient(CIO)

    override suspend fun sendRegistrationRequestToPlatform(platformUrl: URI, dtd: String): Boolean =
        httpClient.post(platformUrl.toString()) {
            url { appendPathSegments(PLATFORM_PATH_SEGMENT) }
            contentType(ContentType.parse("application/td+json"))
            setBody(dtd)
        }.status == HttpStatusCode.Accepted

    override suspend fun sendDeletionRequestToPlatform(resourceUri: URI) {
        httpClient.delete(resourceUri.toString())
    }

    /** Companion object of the [AdapterHttpClientImpl] class. */
    companion object {
        private const val PLATFORM_PATH_SEGMENT = "wodt"
    }
}
