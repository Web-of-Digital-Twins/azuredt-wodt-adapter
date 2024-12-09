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

package application.service

import application.component.PlatformManagementInterface
import infrastructure.testdouble.AdapterHttpClientTestDouble
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.dt.DTUri
import utils.TestingUtils.readResourceFile
import java.net.URI

class PlatformManagementInterfaceTest : StringSpec({
    var platformManagementInterface: PlatformManagementInterface = PlatformManagementInterfaceImpl(
        AdapterHttpClientTestDouble(),
    )
    val platformUri = URI.create("http://platform.it")
    val dtUri = DTUri(URI.create("http://dt.com"))
    val dtd = checkNotNull(readResourceFile("lampTD.json"))

    beforeEach {
        platformManagementInterface = PlatformManagementInterfaceImpl(AdapterHttpClientTestDouble())
    }

    "it should be possible to register to a new platform" {
        platformManagementInterface.requestPlatformRegistration(platformUri, dtUri, dtd) shouldBe true
    }

    "it should not be possible to register to an already registered platform" {
        platformManagementInterface.requestPlatformRegistration(platformUri, dtUri, dtd) shouldBe true
        platformManagementInterface.requestPlatformRegistration(platformUri, dtUri, dtd) shouldBe false
    }

    "a notification for a new platform should be accepted" {
        platformManagementInterface.notifyNewRegistration(dtUri, platformUri) shouldBe true
    }

    "a notification for an already registered platform should not be accepted" {
        platformManagementInterface.notifyNewRegistration(dtUri, platformUri) shouldBe true
        platformManagementInterface.notifyNewRegistration(dtUri, platformUri) shouldBe false
    }

    "a notification for a platform for which has been requested a registration should not be accepted" {
        platformManagementInterface.requestPlatformRegistration(platformUri, dtUri, dtd) shouldBe true
        platformManagementInterface.notifyNewRegistration(dtUri, platformUri) shouldBe false
    }

    "it should not be possible to request a registration for an already notified platform" {
        platformManagementInterface.notifyNewRegistration(dtUri, platformUri) shouldBe true
        platformManagementInterface.requestPlatformRegistration(platformUri, dtUri, dtd) shouldBe false
    }

    "it should be possible to delete from a registered platform" {
        platformManagementInterface.notifyNewRegistration(dtUri, platformUri) shouldBe true
        platformManagementInterface.signalDTDeletion(dtUri) shouldBe true
    }

    "it should not be possible to delete from a not-registered platform" {
        platformManagementInterface.signalDTDeletion(dtUri) shouldBe false
    }

    "it should be possible to request the registration after a deletion" {
        platformManagementInterface.requestPlatformRegistration(platformUri, dtUri, dtd) shouldBe true
        platformManagementInterface.signalDTDeletion(dtUri) shouldBe true
        platformManagementInterface.requestPlatformRegistration(platformUri, dtUri, dtd) shouldBe true
    }

    "it should be possible to receive a notification after a deletion" {
        platformManagementInterface.requestPlatformRegistration(platformUri, dtUri, dtd) shouldBe true
        platformManagementInterface.signalDTDeletion(dtUri) shouldBe true
        platformManagementInterface.notifyNewRegistration(dtUri, platformUri) shouldBe true
    }
})
