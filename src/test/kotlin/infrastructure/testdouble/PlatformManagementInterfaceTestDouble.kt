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

import application.component.PlatformManagementInterface
import model.dt.DTUri
import java.net.URI

/** A simple [PlatformManagementInterface] test double. */
class PlatformManagementInterfaceTestDouble : PlatformManagementInterface {
    val registrations: MutableMap<DTUri, MutableList<URI>> = mutableMapOf()

    override fun requestPlatformRegistration(dtUri: DTUri, platformUrl: URI): Boolean {
        registrations.getOrPut(dtUri) { mutableListOf() } += platformUrl
        return true
    }

    override fun signalDTDeletion(dtUri: DTUri): Boolean {
        registrations.remove(dtUri)
        return true
    }

    override fun get(dtUri: DTUri): List<URI> {
        return registrations.getOrElse(dtUri) { mutableListOf() }
    }

    override fun notifyNewRegistration(dtUri: DTUri, platformUrl: URI): Boolean {
        return this.requestPlatformRegistration(dtUri, platformUrl)
    }
}
