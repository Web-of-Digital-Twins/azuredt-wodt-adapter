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

import application.presenter.adt.DtdlPresentation.toWoTThingDescription
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import model.dt.DTUri
import model.ontology.WoDTVocabulary
import org.eclipse.ditto.json.JsonKey
import org.eclipse.ditto.wot.model.Property
import org.eclipse.ditto.wot.model.SingleRootFormElementOp
import utils.TestingUtils.loadDTsConfiguration
import utils.TestingUtils.readResourceFile
import java.net.URI

class DtdlPresentationTest : StringSpec({
    val dtUri = DTUri(URI.create("http://localhost:5000/lampDT"))
    val azureDTId = "lampDT"
    val platforms = listOf("http://localhost:4000", "http://localhost:6000").map { URI.create(it) }
    val configuration =
        checkNotNull(loadDTsConfiguration("simpleConfiguration.kts").digitalTwinConfigurations[azureDTId])
    val lampDtdl = readResourceFile("./lampDtdl.json").let {
        Json.decodeFromString<JsonObject>(it)
    }

    val dtdlElementType = "@type"
    val dtdlPropertyType = "Property"
    val dtdlName = "name"
    val dtdlContents = "contents"

    "it should be possible to correctly translate a dtdl model to a WoT Thing Description" {
        checkNotNull(
            lampDtdl.toWoTThingDescription(dtUri, platforms, configuration)?.toJsonString(),
        )
            .replace("\n", "")
            .replace(" ", "") shouldBe readResourceFile("lampTD.json").replace("\n", "").replace(" ", "")
    }

    "the root level of a DTD should respect the abstract conceptual model root structure" {
        val dtd = lampDtdl.toWoTThingDescription(dtUri, platforms, configuration)
        dtd shouldNotBe null
        dtd?.also {
            it.id.isPresent shouldBe true
            it.id.get().toString() shouldBe dtUri.uri.toString()
            it.atType.isPresent shouldBe true
            it.version.isPresent shouldBe true
            it.version.get().instance.isPresent shouldBe true
            it.version.get().model.isPresent shouldBe true
            it.wrappedObject.keys.contains(JsonKey.of(WoDTVocabulary.PHYSICAL_ASSET_ID))
            it.properties.isPresent shouldBe true
            it.forms.isPresent shouldBe true
            it.forms.get().any { form -> form.op == SingleRootFormElementOp.OBSERVEALLPROPERTIES } shouldBe true
            it.links.isPresent shouldBe true
            it.links.get()
                .filter { link -> link.rel.isPresent }
                .map { link -> link.rel.get() }
                .containsAll(listOf("type", WoDTVocabulary.DTKG))
            it.links.get()
                .filter { link -> link.rel.isPresent && link.rel.get() == WoDTVocabulary.REGISTERED_TO_PLATFORM }
                .map { link -> link.href.toString() }
                .containsAll(platforms.map { it.toString() })
        }
    }

    fun testTDProperty(property: Property) {
        property.isReadOnly shouldBe true
        property.wrappedObject.keys.contains(JsonKey.of(WoDTVocabulary.DOMAIN_TAG))
    }

    "properties in DTD should respect the abstract conceptual model structure" {
        val dtd = lampDtdl.toWoTThingDescription(dtUri, platforms, configuration)
        lampDtdl[dtdlContents]?.jsonArray
            ?.filter { it.jsonObject[dtdlElementType]?.jsonPrimitive?.content == dtdlPropertyType }
            ?.mapNotNull { it.jsonObject[dtdlName]?.jsonPrimitive?.content }
            ?.also {
                it.forEach {
                    dtd?.properties?.get()[it]?.also {
                        testTDProperty(it)
                        it.wrappedObject.any {
                            it.key.toString() == WoDTVocabulary.AUGMENTED_INTERACTION && !it.value.asBoolean()
                        } shouldBe true
                    } shouldNotBe null
                }
            }?.size shouldNotBe 0
    }

    "relationships in DTD should respect the abstract conceptual model structure" {
        val dtd = lampDtdl.toWoTThingDescription(dtUri, platforms, configuration)
        lampDtdl[dtdlContents]?.jsonArray
            ?.filter { it.jsonObject[dtdlElementType]?.jsonPrimitive?.content != dtdlPropertyType }
            ?.mapNotNull { it.jsonObject[dtdlName]?.jsonPrimitive?.content }
            ?.also {
                it.forEach {
                    dtd?.properties?.get()[it]?.also {
                        testTDProperty(it)
                        it.wrappedObject.any {
                            it.key.toString() == WoDTVocabulary.AUGMENTED_INTERACTION
                        } shouldBe false
                    } shouldNotBe null
                }
            }?.size shouldNotBe 0
    }
})
