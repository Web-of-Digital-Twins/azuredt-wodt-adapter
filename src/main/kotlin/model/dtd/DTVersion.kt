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

package model.dtd

/**
 * Value object that implement the version of the DT, following the semantic versioning rationale.
 * Therefore, is composed of a [major], [minor], and a [patch] version number.
 */
data class DTVersion(val major: Int, val minor: Int, val patch: Int) {
    init {
        require(!(major < 0 || minor < 0 || patch < 0)) { "Value of major/minor/patch must be greater than zero." }
        require(!(major == 0 && minor == 0 && patch == 0)) { "Value of major/minor/patch must not be all zero." }
    }

    override fun toString() = "$major.$minor.$patch"
}
