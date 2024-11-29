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

import application.component.PlatformManagementInterface
import application.component.PlatformManagementInterfaceHttpClient
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import model.dt.DTUri
import utils.URIUtil.relativeResolve
import java.net.URI
import java.util.Collections

/**
 * Implementation of the [application.component.PlatformManagementInterface].
 */
class PlatformManagementInterfaceImpl(
    private val httpClient: PlatformManagementInterfaceHttpClient,
) : PlatformManagementInterface {
    private val registrationMap = Collections.synchronizedMap<DTUri, MutableList<URI>>(mutableMapOf())

    override suspend fun requestPlatformRegistration(platformUrl: URI, dtUri: DTUri, rawDtd: String): Boolean =
        if (registrationMap.contains(dtUri) && registrationMap[dtUri]?.contains(platformUrl) == true) {
            false
        } else {
            httpClient.sendRegistrationRequestToPlatform(platformUrl, rawDtd).also {
                if (it) { registrationMap.addNewPlatform(dtUri, platformUrl) }
            }
        }

    override suspend fun signalDTDeletion(dtUri: DTUri): Boolean =
        coroutineScope {
            if (registrationMap.contains(dtUri)) {
                registrationMap[dtUri]?.forEach {
                    launch {
                        httpClient.sendDeletionRequestToPlatform(
                            URI.create("${it.relativeResolve("wodt/")}${dtUri.uri}"),
                        )
                    }
                }
                registrationMap.remove(dtUri)
                true
            } else {
                false
            }
        }

    override operator fun get(dtUri: DTUri): List<URI> = registrationMap[dtUri]?.toList().orEmpty()

    override fun notifyNewRegistration(dtUri: DTUri, platformUrl: URI): Boolean =
        if (registrationMap.contains(dtUri) && registrationMap[dtUri]?.contains(platformUrl) == true) {
            false
        } else {
            registrationMap.addNewPlatform(dtUri, platformUrl)
            true
        }

    private fun MutableMap<DTUri, MutableList<URI>>.addNewPlatform(dtUri: DTUri, platformUrl: URI) {
        this.getOrPut(dtUri) { Collections.synchronizedList<URI>(mutableListOf()) }.add(platformUrl)
    }
}
