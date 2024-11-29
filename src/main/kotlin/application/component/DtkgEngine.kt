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

import kotlinx.coroutines.flow.Flow
import model.dt.DTUri
import model.dt.Dtkg
import model.semantics.rdf.DTKnowledgeGraph

/** This interface models the DTKG Engine component. */
interface DtkgEngine : DtkgEngineWriter, DtkgEngineReader

/** This interface models the reader part of the [DtkgEngine] component. */
interface DtkgEngineReader {
    /** Obtain the flow of Digital Twin Knowledge Graphs emitted by the component. */
    val dtkgs: Flow<Dtkg>

    /** Get the current DTKG of a DT identified by its [dtUri]. */
    operator fun get(dtUri: DTUri): Dtkg?
}

/** This interface models the writer part of the [DtkgEngine] component. */
interface DtkgEngineWriter {
    /** Update the [dtKnowledgeGraph] of a single DT with id [azureDtId]. */
    fun updateSingleDT(azureDtId: String, dtKnowledgeGraph: DTKnowledgeGraph)

    /** Delete a DT with [dtUri]. */
    fun deleteSingleDT(dtUri: DTUri): Boolean
}
