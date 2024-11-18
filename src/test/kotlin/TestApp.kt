/*
 * Copyright (c) 2024. Andrea Giulianelli
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeExactly

class TestApp : StringSpec({
    "2+2 is equal to 4" {
        2 + 2 shouldBeExactly 4
    }
})
