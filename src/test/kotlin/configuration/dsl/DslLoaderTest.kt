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

import configuration.AdapterDTsConfiguration
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class DslLoaderTest : StringSpec({
    fun loadDsl(fileName: String, imports: String) = DslLoaderImpl().load(
        this::class.java.classLoader.getResource(fileName)?.path.orEmpty(),
        imports,
    )

    "it should be possible to load the Adapter Digital Twins configuration" {
        loadDsl("simpleConfiguration.kts", AdapterDTsConfigurationDsl.DSL_IMPORTS)
            as? AdapterDTsConfiguration shouldNotBe null
    }

    "it should be possible to load and access data of an Adapter Digital Twins configuration" {
        (loadDsl("simpleConfiguration.kts", AdapterDTsConfigurationDsl.DSL_IMPORTS) as? AdapterDTsConfiguration)
            ?.digitalTwinConfigurations?.size shouldBe 1
    }
})
