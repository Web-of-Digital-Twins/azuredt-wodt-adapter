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

import model.dt.DTUri
import java.net.URI

/** This interface models the Platform Management Interface component. */
interface PlatformManagementInterface : PlatformManagementInterfaceReader, PlatformManagementInterfaceNotifier {
    /**
     * Request the registration of the DT, identified by its [dtUri], to the WoDT Platform at [platformUrl].
     * Returns true if successful and not already registered, false instead.
     */
    fun requestPlatformRegistration(dtUri: DTUri, platformUrl: URI): Boolean

    /**
     * Signal to the Platform Management Interface the deletion of the managed Digital Twin with [dtUri].
     * This will result in the deletion notification to be sent to all the WoDT Digital Twins Platform to
     * which it is registered.
     * Returns true if successful, false instead.
     */
    fun signalDTDeletion(dtUri: DTUri): Boolean
}

/** This interface models the external notifier part of the [PlatformManagementInterface] component. */
interface PlatformManagementInterfaceNotifier {
    /**
     * Notify the registration of a DT with [dtUri] to a WoDT Platform with [platformUrl].
     * Returns true if the DT was not already registered to the WoDT Platform, false instead.
     */
    fun notifyNewRegistration(dtUri: DTUri, platformUrl: URI): Boolean
}

/** This interface models the reader part of the [PlatformManagementInterface] component. */
interface PlatformManagementInterfaceReader {
    /** Obtain all the Platform URLs to which the DT, identified by its [dtUri], is registered to. */
    operator fun get(dtUri: DTUri): Set<URI>
}
