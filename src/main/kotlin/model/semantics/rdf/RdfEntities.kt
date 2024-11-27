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

package model.semantics.rdf

import java.net.URI

/**
 * It models the concept of RDF node in the context of a Digital Twin Knowledge Graph.
 * A Node can be anything in the Knowledge Graph from RDF Resources to Literals.
 */
sealed interface RdfNode

/** It models the concept of RDF Literal. It carries a [value] of type [T]. */
data class RdfLiteral<T : Any>(val value: T) : RdfNode

/** It models an RDF resource in the context of a Digital Twin Knowledge Graph. */
sealed interface RdfResource : RdfNode {
    /** The uri of the resource that, in case of a Blank Node can be null. */
    val uri: URI?
}

/** It represents an RDF Resource that has a [uri], e.g., class, property, individual, and so on. */
open class RdfUriResource(override val uri: URI) : RdfResource

/** It represents an RdfClass, identified by its [uri]. */
data class RdfClass(override val uri: URI) : RdfUriResource(uri)

/** It represents an RdfProperty, identified by its [uri]. */
data class RdfProperty(override val uri: URI) : RdfUriResource(uri)

/** It represents an RdfIndividual, identified by its [uri]. */
data class RdfIndividual(override val uri: URI) : RdfUriResource(uri)

/**
 * This class represents a triple without a subject.
 * It is useful when we want to define the [triplePredicate] and the [tripleObject] for an external defined subject.
 */
data class RdfPredicateObjectPair(val triplePredicate: RdfProperty, val tripleObject: RdfNode)

/**
 * It models the concept of RDF Blank Node in the context of Digital Twin Knowledge Graph.
 * A Blank Node has a [blankNodeId] and it has an associated [tripleList].
 */
data class RdfBlankNode(
    val blankNodeId: String,
    val tripleList: List<RdfPredicateObjectPair> = listOf(),
) : RdfResource {
    override val uri = null

    /** Add a [triple] to the blank node. It will return a new blank node with the triple added. */
    fun addTriple(triple: RdfPredicateObjectPair): RdfBlankNode = this.copy(tripleList = tripleList + triple)
}

/**
 * It models the Digital Twin Knowledge graph of a Digital Twin, identified by its [dtUri], in an abstract way.
 * Its objective is to serve as the base to represent semantic data, in particular its [tripleList],
 * about a Digital Twin and not to be a generic implementation of a Knowledge Graph.
 * Its main usage will be as a DTO to be converted in different KG models of different libraries.
 */
data class DTKnowledgeGraph(val dtUri: URI, val tripleList: List<RdfPredicateObjectPair>)
