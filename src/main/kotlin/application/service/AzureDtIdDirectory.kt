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

import configuration.AdapterConfiguration
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import model.dt.DTUri
import java.net.URI

/** Interface that models a directory that translates from DT URIs to Azure DT ids. */
interface AzureDtIdDirectory : AzureDtIdDirectoryReader {
    /** Add an [azureDtId] mapped to a [dtUri] to the directory. */
    suspend fun addDT(azureDtId: String, dtUri: DTUri)

    /** Remove [dtUri] mapping. */
    suspend fun removeDT(dtUri: DTUri)
}

/** Interface that models the reader part of the [AzureDtIdDirectory]. */
interface AzureDtIdDirectoryReader {
    /** Get the azure DT id associated to the [dtUri]. */
    suspend operator fun get(dtUri: DTUri): String?

    /** Get the azure DT id associated to the [dtRelativeUri]. */
    suspend fun getFromRelative(dtRelativeUri: URI): String?
}

/** Simple implementation of the [AzureDtIdDirectory]. It is coroutine compatible and thread-safe. */
class AzureDtIdDirectoryService(private val configuration: AdapterConfiguration) : AzureDtIdDirectory {
    private val mutex = Mutex()
    private var directory: Map<DTUri, String> = mapOf()

    override suspend fun addDT(azureDtId: String, dtUri: DTUri) = mutex.withLock {
        directory = directory + (dtUri to azureDtId)
    }

    override suspend fun removeDT(dtUri: DTUri) {
        directory = directory - dtUri
    }

    override suspend fun get(dtUri: DTUri): String? = mutex.withLock {
        directory[dtUri]
    }

    override suspend fun getFromRelative(dtRelativeUri: URI): String? = mutex.withLock {
        directory.mapKeys { (dtUri, _) -> configuration.exposedUrl.relativize(dtUri.uri) }[dtRelativeUri]
    }
}
