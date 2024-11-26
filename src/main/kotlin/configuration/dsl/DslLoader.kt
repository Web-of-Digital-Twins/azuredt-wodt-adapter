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

import java.io.File
import javax.script.ScriptEngineManager

/** Models a DSL loader. It is useful to load a file that contain a configuration written in some DSL. */
interface DslLoader {
    /** Load and parse the file at [filePath] to construct a Kotlin class instance. In order
     * to be able to compile the script the provided [imports] are attached at the beginning of the file. */
    fun load(filePath: String, imports: String): Any?
}

/** A simple [DslLoader]. */
class DslLoaderImpl : DslLoader {
    override fun load(filePath: String, imports: String): Any? =
        with(ScriptEngineManager().getEngineByExtension("kts")) {
            File(filePath)
                .inputStream()
                .readBytes()
                .toString(Charsets.UTF_8)
                .let { "$imports\n\n$it" }
                .let { this.eval(it) }
        }
}
