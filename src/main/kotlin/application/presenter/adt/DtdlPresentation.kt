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

package application.presenter.adt

import configuration.DigitalTwinConfiguration
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import model.dt.DTUri
import model.ontology.WoDTVocabulary
import model.semantics.DigitalTwinSemantics
import org.eclipse.ditto.wot.model.AtContext
import org.eclipse.ditto.wot.model.AtType
import org.eclipse.ditto.wot.model.IRI
import org.eclipse.ditto.wot.model.Link
import org.eclipse.ditto.wot.model.Properties
import org.eclipse.ditto.wot.model.Property
import org.eclipse.ditto.wot.model.RootFormElement
import org.eclipse.ditto.wot.model.Security
import org.eclipse.ditto.wot.model.SecurityDefinitions
import org.eclipse.ditto.wot.model.SecurityScheme
import org.eclipse.ditto.wot.model.SingleRootFormElementOp
import org.eclipse.ditto.wot.model.SingleUriAtContext
import org.eclipse.ditto.wot.model.ThingDescription
import org.eclipse.ditto.wot.model.Version
import utils.URIUtil.relativeResolve
import java.net.URI
import org.eclipse.ditto.json.JsonObject as DittoJsonObject

/** Module that wraps the DTDL model presentation for this adapter. */
object DtdlPresentation {
    private const val ELEMENT_TYPE = "@type"
    private const val PROPERTY_TYPE = "Property"
    private const val NAME = "name"
    private const val CONTENTS = "contents"
    private const val THING_MODEL_URL = "https://raw.githubusercontent.com/Web-of-Digital-Twins/" +
        "dtd-conceptual-model/refs/heads/main/implementations/wot/dtd-thing-model.tm.jsonld"
    private const val MODEL_VERSION = "1.0.0"

    /**
     * Convert a DTDL model expressed as [JsonObject] to a [ThingDescription].
     * It needs the exposed [dtUri], the [platforms] where it is registered, and the digital twin [configuration].
     */
    fun JsonObject.toWoTThingDescription(
        dtUri: DTUri,
        platforms: List<URI>,
        configuration: DigitalTwinConfiguration,
    ): ThingDescription? =
        platforms.map {
            Link.newBuilder()
                .setHref(IRI.of(it.toString()))
                .setRel(WoDTVocabulary.REGISTERED_TO_PLATFORM)
                .build()
        }.plus(
            listOf(
                Link.newBuilder()
                    .setHref(IRI.of(THING_MODEL_URL))
                    .setRel("type")
                    .setType("application/tm+json")
                    .build(),
                Link.newBuilder()
                    .setHref(IRI.of(dtUri.uri.relativeResolve("dtkg").toString()))
                    .setRel(WoDTVocabulary.DTKG)
                    .build(),
            ),
        ).let { links ->
            ThingDescription.newBuilder()
                .setAtContext(AtContext.newSingleUriAtContext(SingleUriAtContext.W3ORG_2022_WOT_TD_V11))
                .setId(IRI.of(dtUri.uri.toString()))
                .setAtType(
                    AtType.newMultipleAtType(
                        configuration.semantics.types.map { AtType.newSingleAtType(it.uri.toString()) },
                    ),
                )
                .setVersion(
                    Version.newBuilder()
                        .setInstance(configuration.version.toString())
                        .setModel(MODEL_VERSION)
                        .build(),
                )
                .set(WoDTVocabulary.PHYSICAL_ASSET_ID, configuration.physicalAssetID)
                .setSecurityDefinitions(
                    SecurityDefinitions.of(
                        mapOf("nosec_sc" to SecurityScheme.newNoSecurityBuilder("nosec_sc").build()),
                    ),
                )
                .setSecurity(Security.newSingleSecurity("nosec_sc"))
                .setProperties(
                    Properties.from(
                        this[CONTENTS]?.jsonArray?.map { it.toThingDescriptionProperty(configuration.semantics) },
                    ),
                )
                .setForms(
                    listOf(
                        RootFormElement.newBuilder()
                            .setHref(
                                IRI.of(
                                    URI.create(
                                        dtUri.uri.toString().replaceFirst(Regex("([a-zA-Z][a-zA-Z0-9+.-]*):"), "ws:"),
                                    ).relativeResolve("dtkg").toString(),
                                ),
                            )
                            .setSubprotocol("websocket")
                            .setOp(SingleRootFormElementOp.OBSERVEALLPROPERTIES)
                            .build(),
                    ),
                )
                .setLinks(links)
                .build()
        }

    private fun JsonElement.toThingDescriptionProperty(semantics: DigitalTwinSemantics): Property? {
        val type = this.jsonObject[ELEMENT_TYPE]?.jsonPrimitive?.content
        val name = this.jsonObject[NAME]?.jsonPrimitive?.content.orEmpty()

        return semantics.getDomainTag(name)?.uri?.toString()?.let { domainTag ->
            Property.newBuilder(
                name,
                DittoJsonObject.newBuilder()
                    .set(WoDTVocabulary.DOMAIN_TAG, domainTag)
                    .also {
                        if (type == PROPERTY_TYPE) {
                            it.set(WoDTVocabulary.AUGMENTED_INTERACTION, false)
                        }
                    }
                    .build(),
            )
                .setReadOnly(true)
                .build()
        }
    }
}
