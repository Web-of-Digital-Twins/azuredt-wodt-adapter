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

package configuration

import java.net.URI

/**
 * It models the configuration of the Adapter.
 * It has a more static nature.
 */
interface AdapterConfiguration {
    /** Represents the port exposed by the Azure ADT WoDT adapter for its services. */
    val exposedPort: Int

    /** Represents the actual URL exposed publicly by the Azure ADT WoDT Adapter once deployed. */
    val exposedUrl: URI

    /** Represent the ID of an Azure AD application. */
    val azureClientID: String

    /** Represent the ID of the application's Azure AD tenant. */
    val azureTenantID: String

    /** Represent the Azure AD application's client secrets. */
    val azureClientSecret: String

    /** Represent the Azure Digital Twins instance endpoint. */
    val azureDTEndpoint: URI

    /** Represents the Azure SignalR Negotiation url. */
    val signalrNegotiationUrl: URI

    /** Represents the topic name used to publish events on SignalR. */
    val signalrTopicName: String
}
