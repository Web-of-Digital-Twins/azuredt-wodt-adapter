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

import application.component.AdapterHttpClient
import java.net.URI

/** A simple [AdapterHttpClient] that it is always successful. */
class AdapterHttpClientTestDouble : AdapterHttpClient {
    override suspend fun sendRegistrationRequestToPlatform(
        platformUrl: URI,
        dtd: String,
    ): Boolean = true

    override suspend fun sendDeletionRequestToPlatform(resourceUri: URI) {
        // left empty
    }
}
