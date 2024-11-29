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

import java.net.URI

/** This interface models the Adapter HTTP client that has the responsibility to handle all the HTTP operations. */
interface AdapterHttpClient : PlatformManagementInterfaceHttpClient

/** This interface models the HTTP needs for the [PlatformManagementInterface]. */
interface PlatformManagementInterfaceHttpClient {
    /** Send a registration request to a [platformUrl] with the provided [dtd] as a body payload. */
    suspend fun sendRegistrationRequestToPlatform(platformUrl: URI, dtd: String): Boolean

    /** Send a deletion request to a Platform. It needs the complete [resourceUri] on which call delete. */
    suspend fun sendDeletionRequestToPlatform(resourceUri: URI)
}
