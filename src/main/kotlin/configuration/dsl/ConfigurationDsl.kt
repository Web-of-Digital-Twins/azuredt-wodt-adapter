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

package configuration.dsl

import configuration.Configuration
import configuration.DigitalTwinConfiguration
import model.dtd.DTVersion
import model.semantics.DigitalTwinSemantics
import model.semantics.rdf.RdfClass
import model.semantics.rdf.RdfPredicateObjectPair
import model.semantics.rdf.RdfUriResource
import java.net.URI

/** Annotation class for the Configuration DSL. */
@DslMarker
annotation class ConfigurationDSL

/** Extension of the [Configuration] class to create DSL facilities. */
@ConfigurationDSL
class ConfigurationScope : Configuration {
    private var dtConfigurations: Set<DigitalTwinConfiguration> = setOf()

    override val digitalTwinConfigurations
        get() = dtConfigurations

    /** Dsl function to create a Digital Twin Configuration. */
    fun dt(azureID: String, input: DigitalTwinConfigurationScope.() -> Unit) {
        dtConfigurations = dtConfigurations + DigitalTwinConfigurationScope(azureID)
            .apply { input() }.toDigitalTwinConfiguration()
    }
}

/** DSL function to easily create a [Configuration] and configure it via the provided [input]. */
@ConfigurationDSL
fun configuration(input: ConfigurationScope.() -> Unit): Configuration = ConfigurationScope().apply { input() }

/** DSL Scope to define the Digital Twin classes.  */
@ConfigurationDSL
class DigitalTwinClassScope : MutableList<RdfClass> by mutableListOf() {
    /** Add a class to the scope with the -. */
    operator fun String.unaryMinus() = apply { add(RdfClass(URI.create(this))) }
}

/** DSL Scope to define the Domain tags associated to the elements of the model. */
@ConfigurationDSL
class DomainTagScope : MutableMap<String, RdfUriResource> by mutableMapOf() {
    /** Add a <element, domain tag> entry to the scope with the -. */
    operator fun Pair<String, URI>.unaryMinus() = apply { put(this.first, RdfUriResource(this.second)) }
}

/** DSL Scope to define the data mapping. */
@ConfigurationDSL
class DataMappingScope<T : Any> : MutableMap<String, (T) -> List<RdfPredicateObjectPair>> by mutableMapOf() {
    /** Add a data mapping to the scope with the -. */
    operator fun Pair<String, (T) -> List<RdfPredicateObjectPair>>.unaryMinus() = apply { put(this.first, this.second) }
}

/** Extension of the [DigitalTwinSemantics] class to create DSL facilities. */
@ConfigurationDSL
class DigitalTwinSemanticsScope : DigitalTwinSemantics {
    private var _types: List<RdfClass> = listOf()
    private var _domainTags: Map<String, RdfUriResource> = mapOf()
    private var _propertyMapping: Map<String, (Any) -> List<RdfPredicateObjectPair>> = mapOf()
    private var _relationshipMapping: Map<String, (URI) -> List<RdfPredicateObjectPair>> = mapOf()

    /** Specify the classes of the Digital Twin with the provided [configuration]. */
    fun classes(configuration: DigitalTwinClassScope.() -> Unit) {
        _types = DigitalTwinClassScope().apply { configuration() }
    }

    /** Specify the domain tags of the Digital Twin elements with the provided [configuration]. */
    fun domainTags(configuration: DomainTagScope.() -> Unit) {
        _domainTags = DomainTagScope().apply { configuration() }
    }

    /** Specify the property mappings with the provided [configuration]. */
    fun propertyMapping(configuration: DataMappingScope<Any>.() -> Unit) {
        _propertyMapping = DataMappingScope<Any>().apply { configuration() }
    }

    /** Specify the relationship mappings with the provided [configuration]. */
    fun relationshipMapping(configuration: DataMappingScope<URI>.() -> Unit) {
        _relationshipMapping = DataMappingScope<URI>().apply { configuration() }
    }

    override val types: List<RdfClass>
        get() = _types

    override fun getDomainTag(elementName: String): RdfUriResource? = _domainTags[elementName]

    override fun <T : Any> mapData(
        propertyName: String,
        value: T,
    ): List<RdfPredicateObjectPair>? = _propertyMapping[propertyName]?.invoke(value)

    override fun mapData(
        relationshipName: String,
        targetDT: URI,
    ): List<RdfPredicateObjectPair>? = _relationshipMapping[relationshipName]?.invoke(targetDT)
}

/** DSL function to easily create an [URI] from a [stringRepresentation]. */
fun uri(stringRepresentation: String): URI = URI.create(stringRepresentation)

/** DSL Scope to build a [DigitalTwinConfiguration] of an Azure Digital Twin identified by its [azureID]. */
@ConfigurationDSL
class DigitalTwinConfigurationScope(var azureID: String) {
    /** Digital Twin relative uri with respect to the adapter uri. */
    lateinit var relativeUri: URI

    /** The WoDT Digital Twin version. */
    lateinit var version: DTVersion

    /** The associated Physical Asset ID. */
    lateinit var physicalAssetID: String

    /** The platform to automatic register the DT. */
    lateinit var platformsToRegister: List<URI>

    private lateinit var _semantics: DigitalTwinSemantics

    /** Specify the Digital Twin semantics via its [configuration]. */
    fun semantics(configuration: DigitalTwinSemanticsScope.() -> Unit) {
        _semantics = DigitalTwinSemanticsScope().apply { configuration() }
    }

    /** Build the corresponding [DigitalTwinConfiguration]. */
    fun toDigitalTwinConfiguration() = DigitalTwinConfiguration(
        azureID,
        relativeUri,
        version,
        physicalAssetID,
        platformsToRegister,
        _semantics,
    )
}
