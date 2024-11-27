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

package model.ontology

/** This module wraps the needed elements from the WoDT Vocabulary. */
object WoDTVocabulary {
    /** Base URI of the vocabulary. */
    const val BASE_URI = "https://purl.org/wodt/"

    /** Predicate to link the dtd to the dtkg. */
    const val DTKG = BASE_URI + "dtkg"

    /** Domain tag predicate to semantically annotate DT model. */
    const val DOMAIN_TAG = BASE_URI + "domainTag"

    /** Predicate to link to the associated DTD. */
    const val DTD = BASE_URI + "dtd"

    /** Registered to platform predicate. */
    const val REGISTERED_TO_PLATFORM = BASE_URI + "registeredToPlatform"

    /** Physical Asset id predicate. */
    const val PHYSICAL_ASSET_ID = BASE_URI + "physicalAssetId"

    /** Augmented Interaction predicate. */
    const val AUGMENTED_INTERACTION = BASE_URI + "augmentedInteraction"
}
