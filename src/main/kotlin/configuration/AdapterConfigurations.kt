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

/** [AdapterConfiguration] implementation with Environmental Variables. */
class EnvironmentAdapterConfiguration : AdapterConfiguration {
    init {
        checkNotNull(System.getenv(EXPOSED_PORT_VARIABLE)) { "Please provide the exposed port" }
        checkNotNull(System.getenv(EXPOSED_URL_VARIABLE)) { "Please provide the exposed url" }
        checkNotNull(System.getenv(AZURE_CLIENT_ID_VARIABLE)) { "Please provide the Azure AD application client ID" }
        checkNotNull(System.getenv(AZURE_TENANT_ID_VARIABLE)) { "Please provide the Azure Tenant" }
        checkNotNull(System.getenv(AZURE_CLIENT_SECRET_VARIABLE)) { "Please provide the Azure AD application secret" }
        checkNotNull(System.getenv(AZURE_DT_ENDPOINT_VARIABLE)) { "Please provide the Azure Digital Twins endpoint" }
        checkNotNull(System.getenv(SIGNALR_NEGOTIATION_URL_VARIABLE)) { "Please provide the SignalR negotiation url" }
        checkNotNull(System.getenv(SIGNALR_TOPIC_NAME_VARIABLE)) { "Please provide the SignalR topic name" }
    }

    override val exposedPort: Int = System.getenv(EXPOSED_PORT_VARIABLE).toInt()
    override val exposedUrl: URI = URI.create(System.getenv(EXPOSED_URL_VARIABLE))
    override val azureClientID: String = System.getenv(AZURE_CLIENT_ID_VARIABLE)
    override val azureTenantID: String = System.getenv(AZURE_TENANT_ID_VARIABLE)
    override val azureClientSecret: String = System.getenv(AZURE_CLIENT_SECRET_VARIABLE)
    override val azureDTEndpoint: URI = URI.create(System.getenv(AZURE_DT_ENDPOINT_VARIABLE))
    override val signalrNegotiationUrl: URI = URI.create(System.getenv(SIGNALR_NEGOTIATION_URL_VARIABLE))
    override val signalrTopicName: String = System.getenv(SIGNALR_TOPIC_NAME_VARIABLE)

    /** Companion object that contains the required environmental variables. */
    companion object {
        private const val EXPOSED_PORT_VARIABLE = "EXPOSED_PORT"
        private const val EXPOSED_URL_VARIABLE = "EXPOSED_URL"
        private const val AZURE_CLIENT_ID_VARIABLE = "AZURE_CLIENT_ID"
        private const val AZURE_TENANT_ID_VARIABLE = "AZURE_TENANT_ID"
        private const val AZURE_CLIENT_SECRET_VARIABLE = "AZURE_CLIENT_SECRET"
        private const val AZURE_DT_ENDPOINT_VARIABLE = "AZURE_DT_ENDPOINT"
        private const val SIGNALR_NEGOTIATION_URL_VARIABLE = "SIGNALR_NEGOTIATION_URL"
        private const val SIGNALR_TOPIC_NAME_VARIABLE = "SIGNALR_TOPIC_NAME"
    }
}
