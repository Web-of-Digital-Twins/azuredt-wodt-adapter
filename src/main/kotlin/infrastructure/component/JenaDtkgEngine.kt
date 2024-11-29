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

package infrastructure.component

import application.component.DtkgEngine
import configuration.AdapterDTsConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import model.dt.DTUri
import model.dt.Dtkg
import model.semantics.rdf.DTKnowledgeGraph
import model.semantics.rdf.RdfBlankNode
import model.semantics.rdf.RdfLiteral
import model.semantics.rdf.RdfPredicateObjectPair
import model.semantics.rdf.RdfProperty
import model.semantics.rdf.RdfUriResource
import org.apache.jena.rdf.model.AnonId
import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.rdf.model.Resource
import org.apache.jena.riot.Lang
import org.apache.jena.riot.RDFWriter
import org.apache.jena.shared.Lock
import org.apache.jena.vocabulary.RDF
import java.net.URI
import kotlin.coroutines.CoroutineContext

/** This class implement a Jena-based [DtkgEngine]. */
class JenaDtkgEngine(
    private val configuration: AdapterDTsConfiguration,
    private val context: CoroutineContext = Dispatchers.Default,
) : DtkgEngine {
    private val _dtkgs = MutableSharedFlow<Dtkg>()
    private val models: MutableMap<DTUri, Model> = mutableMapOf()

    override val dtkgs: Flow<Dtkg> = _dtkgs.asSharedFlow()

    override fun updateSingleDT(azureDtId: String, dtKnowledgeGraph: DTKnowledgeGraph) {
        models.getOrPut(dtKnowledgeGraph.dtUri) { ModelFactory.createDefaultModel() }.write {
            removeAll()
            createResource(dtKnowledgeGraph.dtUri.uri.toString())
                .addProperties(
                    this,
                    configuration.digitalTwinConfigurations[azureDtId]?.semantics?.types?.map {
                        RdfPredicateObjectPair(
                            RdfProperty(URI.create(RDF.type.uri)),
                            it,
                        )
                    }.orEmpty(),
                )
                .addProperties(this, dtKnowledgeGraph.tripleList)
            CoroutineScope(context).launch { _dtkgs.emit(Dtkg(dtKnowledgeGraph.dtUri, toTurtle())) }
        }
    }

    override fun deleteSingleDT(dtUri: DTUri): Boolean = models.remove(dtUri) != null

    override operator fun get(dtUri: DTUri): Dtkg? = models[dtUri]?.read { Dtkg(dtUri, toTurtle()) }

    private fun Resource.addProperties(dtModel: Model, tripleList: List<RdfPredicateObjectPair>): Resource =
        tripleList.fold(this) { dtResource, (triplePredicate, tripleObject) ->
            val property = dtModel.createProperty(triplePredicate.uri.toString())
            when (tripleObject) {
                is RdfBlankNode ->
                    dtResource.addProperty(
                        property,
                        dtModel.createResource(AnonId(tripleObject.blankNodeId))
                            .addProperties(dtModel, tripleObject.tripleList),
                    )
                is RdfLiteral<*> ->
                    dtResource.addLiteral(property, tripleObject.value)
                is RdfUriResource ->
                    dtResource.addProperty(property, model.createResource(tripleObject.uri.toString()))
            }
        }

    private fun Model.toTurtle() = RDFWriter.create().lang(Lang.TTL).source(this).asString()

    private fun Model.write(operations: Model.() -> Unit) {
        this.criticalSection(Lock.WRITE, operations)
    }

    private fun <T : Any> Model.read(operations: Model.() -> T): T {
        return this.criticalSection(Lock.READ, operations)
    }

    private fun <T : Any> Model.criticalSection(mode: Boolean, operations: Model.() -> T): T {
        this.enterCriticalSection(mode)
        return this.operations().also { this.leaveCriticalSection() }
    }
}
