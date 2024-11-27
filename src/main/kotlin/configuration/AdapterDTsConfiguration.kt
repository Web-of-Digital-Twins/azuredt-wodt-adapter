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

package configuration

import model.dt.DTVersion
import model.semantics.DigitalTwinSemantics
import java.net.URI

/** It models the Configuration of the adapter in terms of the Digital Twins to expose. It has a more dynamic nature. */
interface AdapterDTsConfiguration {
    /**
     * The configurations of the Digital Twins to expose.
     * A map from the Azure DT ID to the associated configuration.
     */
    val digitalTwinConfigurations: Map<String, DigitalTwinConfiguration>
}

/**
 * This class represents a single Digital Twin configuration.
 * Currently, the data needed to expose a single DT is:
 * - its [azureID] on the Azure Digital Twins instance
 * - the [relativeUri] with respect to the adapter url where expose the DT
 * - the [version] of the exposed DT
 * - the associated [physicalAssetID]
 * - the list of [platformsToRegister]
 * - the DT [semantics]
 */
data class DigitalTwinConfiguration(
    val azureID: String,
    val relativeUri: URI,
    val version: DTVersion,
    val physicalAssetID: String,
    val platformsToRegister: List<URI>,
    val semantics: DigitalTwinSemantics,
)
