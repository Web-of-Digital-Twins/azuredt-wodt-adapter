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
import java.net.URI

/** Value object that represents the DT [uri]. */
@JvmInline
value class DTUri(val uri: URI) {
    /** Companion object of [DTUri]. */
    companion object {
        /** Resolve a [DTUri] from its [azureId] and the provided [configuration].  */
        fun fromAzureID(azureId: String, configuration: Configuration): DTUri? =
            configuration.digitalTwinConfigurations[azureId]?.relativeUri?.let {
                DTUri(configuration.exposedUrl.resolve(it))
            }
    }
}
