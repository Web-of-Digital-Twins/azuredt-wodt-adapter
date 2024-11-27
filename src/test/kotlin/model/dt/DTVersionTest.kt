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

package model.dt

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DTVersionTest : StringSpec({
    "version is correctly converted in string" {
        DTVersion(1, 2, 3).toString() shouldBe "1.2.3"
    }

    "version cannot contain negative numbers" {
        shouldThrow<IllegalArgumentException> { DTVersion(-1, 0, 0) }
        shouldThrow<IllegalArgumentException> { DTVersion(0, -1, 0) }
        shouldThrow<IllegalArgumentException> { DTVersion(0, 0, -1) }
    }

    "version cannot be zero" {
        shouldThrow<IllegalArgumentException> { DTVersion(0, 0, 0) }
    }
})
