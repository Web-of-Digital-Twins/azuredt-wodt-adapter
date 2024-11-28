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

import kotlinx.coroutines.Deferred
import org.eclipse.ditto.wot.model.ThingDescription

/** This interface models the DTD Manager component. */
interface DtdManager {
    /**
     * Obtain the Digital Twin Description of the Azure DT with the provided [azureDtId].
     * The adapter implements the DTD with the Thing Description.
     */
    suspend operator fun get(azureDtId: String): Deferred<ThingDescription?>
}
