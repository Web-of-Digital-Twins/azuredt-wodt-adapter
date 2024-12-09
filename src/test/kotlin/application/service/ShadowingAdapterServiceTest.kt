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

import infrastructure.testdouble.AzureDTClientTestDouble
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import model.dt.DTUri
import model.event.CreateEvent
import model.event.DeleteEvent
import model.event.ShadowingEvent
import model.event.UpdateEvent
import utils.TestingUtils.loadConfiguration

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("InjectDispatcher")
class ShadowingAdapterServiceTest : StringSpec({
    "it should filter out non relevant events" {
        runTest {
            val configuration = checkNotNull(loadConfiguration("simpleConfiguration.kts"))
            val shadowingAdapterService = ShadowingAdapterService(configuration, AzureDTClientTestDouble())
            val dtId = "lampDT"
            val eventList: MutableList<ShadowingEvent> = mutableListOf()

            val job = launch(Dispatchers.Unconfined) { shadowingAdapterService.events.collect { eventList.add(it) } }
            shadowingAdapterService.start()

            advanceUntilIdle()
            job.cancel()

            eventList.all {
                when (it) {
                    is UpdateEvent -> it.azureDtId == dtId
                    is CreateEvent -> it.azureDtId == dtId
                    is DeleteEvent -> it.dtUri == DTUri.fromAzureID(dtId, configuration)
                }
            } shouldBe true
        }
    }
})
