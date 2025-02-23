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

package application.presenter.api

import kotlinx.serialization.Serializable

/**
 * Presenter class to be able to deserialize platform registration data from the API.
 * It contains the [self] field where the WoDT Digital Twins Platform send its URL and the registered [dtUri].
 */
@Serializable
data class PlatformRegistration(val self: String, val dtUri: String)
