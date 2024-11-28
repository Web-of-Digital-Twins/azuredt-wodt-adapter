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

package utils

import configuration.AdapterDTsConfiguration
import configuration.dsl.AdapterDTsConfigurationDsl
import configuration.dsl.DslLoaderImpl

/** Module that wraps some testing utilities. */
object TestingUtils {
    /** Read a file content from resource. */
    fun readResourceFile(fileName: String) =
        checkNotNull(this::class.java.getClassLoader().getResource(fileName)?.readText(Charsets.UTF_8))

    fun loadConfiguration(fileName: String) = DslLoaderImpl().load(
        this::class.java.classLoader.getResource(fileName)?.path.orEmpty(),
        AdapterDTsConfigurationDsl.DSL_IMPORTS,
    ) as AdapterDTsConfiguration
}
