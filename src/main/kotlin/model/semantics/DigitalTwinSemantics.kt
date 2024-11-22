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

package model.semantics

import model.semantics.rdf.RdfClass
import model.semantics.rdf.RdfPredicateObjectPair
import model.semantics.rdf.RdfUriResource
import java.net.URI

/**
 * This interface models the entire semantics of the Digital Twin useful to create the HWoDT Uniform Interface.
 * This interface once implemented, it has the responsibility of obtaining the Digital Twin classes, the domain tags,
 * and map raw data to RDF data, considering the domain ontology.
 */
interface DigitalTwinSemantics {
    /** The RdfClasses that represent the domain-oriented types of the Digital Twin.  */
    val types: List<RdfClass>

    /**
     * Get the Domain Tag of a Digital Twin model element -- property or relationship -- based on its [elementName]
     * defined in the DTDL model.
     * It returns the associated [RdfUriResource] that could be a class, a property or an individual.
     * If no element is defined with the specified [elementName] null is returned.
     */
    fun getDomainTag(elementName: String): RdfUriResource?

    /**
     * Map the [value] of a Digital Twin property with [propertyName] and a type [T] in
     * a list of [RdfPredicateObjectPair]. If the [propertyName] is not mapped then null is returned.
     */
    fun <T : Any> mapData(propertyName: String, value: T): List<RdfPredicateObjectPair>?

    /**
     * Map the [targetDT] instance of a Digital Twin relationship with [relationshipName] in a list
     * of [RdfPredicateObjectPair]. If the [relationshipName] is not mapped then null is returned.
     */
    fun mapData(relationshipName: String, targetDT: URI): List<RdfPredicateObjectPair>?
}
