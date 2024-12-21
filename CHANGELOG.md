## [1.0.4](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/compare/1.0.3...1.0.4) (2024-12-21)

### Dependency updates

* **core-deps:** update dependency org.jetbrains.kotlinx:kotlinx-coroutines-core to v1.10.1 ([2a229a1](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/2a229a1eec5cf6ccaf3e44fcb6c76a756a11051d))

## [1.0.3](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/compare/1.0.2...1.0.3) (2024-12-20)

### Dependency updates

* **core-deps:** update dependency org.jetbrains.kotlinx:kotlinx-coroutines-core to v1.10.0 ([fb81d90](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/fb81d9064d7397d3cd1c15a9bb967bb805115c87))
* **deps:** update ktor monorepo to v3.0.3 ([564e01c](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/564e01c917b403c04c380b2db71f5edca8d2ad88))
* **deps:** update plugin com.gradle.develocity to v3.19 ([0c12bae](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/0c12baea358ce74a2f0868770f3facb386397472))
* **deps:** update plugin dokka to v2 ([9e08b35](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/9e08b35efddd14e8fad58ac4959255b1d41aa077))
* **deps:** update plugin org.danilopianini.gradle-pre-commit-git-hooks to v2.0.16 ([53601db](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/53601dbaf9f5640d4c567dd569eed8061b550440))
* **deps:** update plugin org.danilopianini.gradle-pre-commit-git-hooks to v2.0.17 ([c30fd14](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/c30fd1424b1e79b85252cda367819e57d842a073))

### Build and continuous integration

* **deps:** update codecov/codecov-action action to v5.1.2 ([d1e9877](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/d1e9877cd55f0951f825575a02683a307a23fa5b))

## [1.0.2](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/compare/1.0.1...1.0.2) (2024-12-10)

### Dependency updates

* **deps:** add archunit dependency ([e196c91](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/e196c9175be3d6fee77e2f5a09b335fdfcb4d0cd))
* **deps:** add client content negotiation plugin for tests ([ba17c47](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/ba17c477b9582332a4dec98bf8ae9eb30857d7b4))
* **deps:** update alpine docker tag to v3.21 ([88e0263](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/88e0263cf0a2bd2ae9eab04e0af2731e59b7da2a))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.116 ([e3dbbc4](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/e3dbbc4d2ee2dcba0ffd85dca99cbbf52b157d1f))
* **deps:** update ktor monorepo to v3.0.2 ([e560660](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/e560660f24ecaa92bbb02d90c3d804c71ead804c))
* **deps:** update node.js to 22.12 ([d1ada63](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/d1ada6384282655d3d28d56a73ad0fd96d224a80))

### Bug Fixes

* correct relative resolve functionality to ensure that users always have the expected behavior ([1c5844d](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/1c5844df72f2277d413e8e591d8073296dc33c92))

### Tests

* add adapter http client test double ([8ad43b4](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/8ad43b488f320d4a2237473cb341e58d65e08043))
* add dtkg engine tests ([66f7cb8](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/66f7cb82304fa77c82bfba2dc49e1e55cb4cc778))
* add event to exclude ([b04e8f2](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/b04e8f2a3844cc2e82585c2f565c65a4bbdb44f6))
* add ktor testing utils ([dc7482b](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/dc7482bb5c76c18a0a3987c83f245fa4ab30b505))
* add platform management interface service tests ([fcb5cff](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/fcb5cffaab48e39069fe3db8b9f72d739060711e))
* add shadowing adapter event filtering test ([9efdfaa](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/9efdfaa0244a9c7cc0a30fc5f198c0425077e8fb))
* add test to ensure that relative resolve should work even when original resolve do not consider relative uri ([0a2bcb5](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/0a2bcb5bce444a4a0a95d49c36b32128d53ed2fa))
* add tests for dt uri factory ([3e1d88a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3e1d88a366d4fce3b3f34b906be2744a975d59a2))
* add tests for dtdl and td presentation ([61ec497](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/61ec4973f1a4b60812ae84abf8eebbcc992f744f))
* add tests for platform management interface api ([b5240cc](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/b5240cc861c0c4d4bd984705157112a9d923feaf))
* add tests for wodt digital twin interface api ([a4004ac](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/a4004ac2ca7b907c9d87d628b2de6125b1b08986))
* add tests for wot dtd manager ([0fca266](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/0fca26644d46e0afa9938daef500df9b18f89dd9))
* **architecture:** add architectural tests to respect clean architecture ([813b645](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/813b64596145f6ed00ed11159845be135409a94e))
* include platform management interface api and service in testing utils ([35be019](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/35be019346c5f35f332a73d7fc96fdecc2e3d045))

### Build and continuous integration

* **deps:** update codecov/codecov-action action to v5.1.1 ([3a530b7](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3a530b7562584130050fa210ffcb886ce82e3fe6))
* update project name ([f528b0d](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/f528b0da09693c3bc3f37856b9eb0b2a9c26d651))

### General maintenance

* add ExternalDT dtdl model ([05fe7c5](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/05fe7c56bb888e24bfb6c4d984e50606b8674f00))
* add required pipeline image ([9fe716e](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/9fe716e80b84e3173dca5765c09705d1db171cb0))
* update README ([f94df41](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/f94df418d42e83d9314712134bb91271fe177504))

### Style improvements

* **test:** adhere to kotlin style guide ([a6f6a56](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/a6f6a566bbc2ef16c5b6b5e356359a5e83408b6f))

### Refactoring

* extract infrastructure responsibilities to create an api independent shadowing adapter ([2846fa7](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/2846fa7706c477743ce47f9642e34299d1fcbf57))

## [1.0.1](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/compare/1.0.0...1.0.1) (2024-12-02)

### Dependency updates

* **deps:** update dependency io.github.oshai:kotlin-logging-jvm to v7.0.3 ([60ae71d](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/60ae71d4cda272cd9c8975b3eb7866e17daef846))
* **deps:** update dependency org.eclipse.ditto:ditto-wot-model to v3.6.3 ([03688fd](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/03688fd5678489c7dc57fd24d1aa6be8d9f0860b))

### Bug Fixes

* correct order of components ([3423d19](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3423d199f064e4f7cb422a26187f1e1c90009298))

## 1.0.0 (2024-11-30)

### Features

* add dts configuration loading to configuration loader ([08a78ed](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/08a78eda8fb55afec9e13750cc55dc94692c398c))
* allow to not specify platforms to register the dt in the configuration dsl ([762c6da](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/762c6da301f7d212b41712c64063d7c3bf1a4906))
* create the adapter web server and the platform management interface api ([4df0665](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/4df0665c23e7607d0e633401f913f8b55d97fd0c))
* create the platform management interface service ([ff8a59c](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/ff8a59cb0fb9de976134db6b3faac6f4508894aa))
* **dsl:** add adapter configuration dsl ([872cec2](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/872cec26bbe281eed056d21ddf9b801b5f047cc7))
* first version of the shadowing adapter based on SignalR ([2c3b383](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/2c3b383ed85a7d789e8996a2509a5c677e76328b))
* handle the flow with dtkg engine and the platform management interface ([3b47b19](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3b47b1997b78a95b8e019e0ceda2a0cb4938f422))
* implement and add the wodt digital twin interface component ([2f43d18](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/2f43d18257ac87be91dd2af6361e7a02eabf23ad))
* implement first version of the WoT DTD Manager ([f92135e](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/f92135eccf4d3615011473fafd5be2c74c87f973))
* implement the dtkg engine component ([975805c](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/975805c9077caba97da6f1e495d1a1f279a95cc1))
* initialize shadowing with already existing dts in the azure instance ([258961f](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/258961faaa34dcd52054caccdbf2f663023a1d7a))
* provide adapter configuration via environmental variables ([0af6ede](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/0af6ede72eaf66f361b4ddaf7da83aacd73900d5))

### Dependency updates

* **core-deps:** update kotlin monorepo to v2.1.0 ([0e90e41](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/0e90e41dd39145c22c063ba4796b73a4d3d2c55f))
* **deps:** add azure digital twins and identity dependencies ([c48eab4](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/c48eab475f6daad99ab6151facec8a162ee74d18))
* **deps:** add azure signalr dependency ([20b643b](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/20b643bbe8fd68e5fdc1aa65f6f83826dcc58b49))
* **deps:** add ditto wot model dependency ([80b5e39](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/80b5e39a624388f99e9c48da98cb6632ce8cba63))
* **deps:** add jena dependency ([d8b1881](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/d8b18810f019e01f1a22b3039634253982a7520e))
* **deps:** add kotlin coroutine dependency ([6d1f415](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/6d1f4154ab88264cc43c90080d446740e8cf813a))
* **deps:** add kotlin scripting dependency ([3923f9a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3923f9a156c3236ee33a2b5367b2b2a021bf7714))
* **deps:** add kotlin serialization and json serialization dependencies ([3e3e481](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3e3e481cdf6aa777bcad6c38ebe07178b55462d4))
* **deps:** add ktor dependencies ([ff423be](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/ff423bece42f3a544c0a96cc12ef963e9f234f8d))
* **deps:** add logging and sl4j dependencies ([ed714a5](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/ed714a5e7ac85659b2b2e41daf0459d61f0b2667))
* **deps:** update dependency gradle to v8.11 ([6869728](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/686972862eb308234797a677d7951001681c6c01))
* **deps:** update dependency gradle to v8.11.1 ([b1b3ee8](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/b1b3ee85b17f88d29977aeea3c0d6ba2a82db5b6))
* **deps:** update gradle-kotlin-qa to version 0.70.2 ([17c2240](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/17c22408aac7694839fad1d32357617536116e8d))
* **deps:** update plugin com.gradle.develocity to v3.18.2 ([#7](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/issues/7)) ([8d12fa4](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/8d12fa45b269c4018fe06c002de49e5e43428f82))
* **deps:** update plugin org.danilopianini.gradle-pre-commit-git-hooks to v2.0.15 ([#6](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/issues/6)) ([85379fa](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/85379fa5b396fd7d9f6ad75653d53f9271648bcf))

### Bug Fixes

* correct dispatcher with respect to IO work load ([5884998](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/5884998c5f460510613d6c452b5ba295945bd907))
* fix imports ([3747f11](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3747f111abc1e04708a345f4ac529a11cd61f958))
* solve URI relative resolve bug when adding a relative path to an uri with existing path elements ([88d4263](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/88d4263f35c99d37314153f5048547e5c4350ac7))

### Documentation

* align docs to parameters ([4c66536](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/4c66536db7ca75522aac3465d038c257424949fd))
* improve docs ([ea5581e](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/ea5581e779f081c20210b5a4170ff7c9cde11723))
* **rest-api:** add rest api swagger documentation ([acf044c](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/acf044ce6c4ceeefbf73c2679d2fa843528c1f41))

### Tests

* add signalr event resource files ([df52967](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/df52967c4cdf82c75cc0379507e333a5f0787cf4))
* add test for azure digital twin current state presentation classes ([62f4592](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/62f459275fb45dffd896b38680b7db3fdd2d797e))
* add test for dtdl presentation ([dae61d2](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/dae61d2685d4b5595987ee7a84d741c151f91772))
* add test utilities to load resource files and configuration ([1e5c39c](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/1e5c39c00d10d0a4cc56fc83f76d3bc39a6d692a))
* add tests and align names ([d655140](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/d6551407ffba591ecd3f060c4bfaee4f744ecee6))
* add tests for adt presentation ([efbf637](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/efbf6373c7278d629808f595ba1d95253d75f47d))
* add tests for dsl loader ([2115f21](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/2115f21cab1f7ba0854e5ee07b72537bcaabaa85))
* add tests for dt version value object ([e129e7a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/e129e7a85646583a8f284b4d1529a55a94ccf1b9))
* add tests for id directory service ([9cb68b3](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/9cb68b3cc889b8eacea8b9dc91a626ee0737c384))
* add tests for uri utils ([32c025f](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/32c025fdd27a4ad0b925918a30104dad4f07b45c))
* add tests for WoT DTD Manager ([d58b28d](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/d58b28d346c1cf46723b0f88c809df31333065bf))
* create azure dt client test double ([92bb290](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/92bb290ff32aab54958c7063c8fc5902940d43b7))
* create platform management interface test double ([f51dff5](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/f51dff52aafdb871478e0f3aff9e2750336ababc))
* **dsl:** add tests for the adapter configuration dsl ([3e94617](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3e9461744bf449500a1bace11f6ff30faca6f70c))
* **fix:** align platform management interface test double to new interface ([a69a226](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/a69a226810ba4b9aab08bfd77081b0a01d752c4f))
* **fix:** align with update event parameters ([46f89ac](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/46f89ac8e1648eaf38a19fc3c45c0b4ce4b911de))
* **fix:** fix resource file names and reader ([65a3854](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/65a385488ef243c4680f01224c04b71e38646947))
* **resource:** fix simple configuration error ([954c8d5](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/954c8d5ddbb9dcc3d3282ab23c5ae97ee6ea0f01))

### Build and continuous integration

* **deps:** update codecov/codecov-action action to v5.0.4 ([4f96043](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/4f960435fcd52a30ee155ba5a77dd039f59067a8))
* **deps:** update codecov/codecov-action action to v5.0.7 ([692d1eb](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/692d1ebed4fa32a7ccf926a1bc0c86f364cdc552))
* set java matrix and change documentation action ([82e271b](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/82e271bbdc5b75868b7fae885f294d7fd3159bb5))
* streamline semantic release configuration ([9d9b9bb](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/9d9b9bb2e312c102faf0545c515c79f0f59249d0))
* update group ([8773ff6](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/8773ff6a0e8cfcb6cb4fa5d7c7bde5da45e524ac))

### General maintenance

* add a simple configuration to test resources ([b44099f](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/b44099f7b4f32d25de81d59b996fb8906de49e6d))
* add azure digital twin id to create event ([2d1ff70](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/2d1ff7019c3bebe3c07be084528bba25bc6abc58))
* add azure dt id to update event to simplify next computations ([bc854c2](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/bc854c2f7f1d192b9dfcdd2110909adcea1ea1a9))
* add dsl imports ([bb4000a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/bb4000a5dd9b98c053753e2bae4bbc601044e55c))
* add dtdl presentation class ([907595f](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/907595f4aa7c910f9c2cd9431d403dd4ebde0ed5))
* add signalr data presentation ([0f93c33](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/0f93c338643fc8e87fa4d50a168b27946e36d92e))
* add support for fragments ([77bc928](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/77bc928fca6f6911b01d06653e1c3915edda3d51))
* add the adapter http client ([61b103d](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/61b103d5d1d2096af3e83c0f3dd90ca8f3b461ef))
* add the possibility to delete mapping in Azure DT Id Directory ([f2a1046](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/f2a1046cdfedee494750c3b6176c58395fe40972))
* add wodt vocabulary ([3333d12](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3333d12245679a5c91358ad83fee2229ea40e4f6))
* allow to retrieve azure id from relative dt uri ([e7d65a2](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/e7d65a2dd8dae96e47f96940dbec294240b16e6e))
* create a more generic concept of adapter directory ([ffc4135](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/ffc4135fe59d7eedf92d400c89fa5aee375aa245))
* create azure digital twin current state presentation classes ([b30b912](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/b30b9128c684c1f16e12381d50e1887bac73c82d))
* create azure digital twins client ([3c730c5](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/3c730c574b02506bb8990ef5d6752d5f88381251))
* create directory to map dt uris to azure ids ([06745d1](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/06745d11ac592e5cfd22722ce7e817cf8b5ac8fb))
* create dsl loader concept and implementation ([2da198b](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/2da198b34b3e1c0184ac4538135852f55f439832))
* create entry point ([6037041](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/603704178afae8b1c33cd1bf7507522735912fa7))
* create the adapter engine service ([6c55c8a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/6c55c8a93f47ddedae73b8432be6ee5aa6d1818e))
* create utils to load a configuration test double ([ea17204](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/ea17204ec65eeb0b29739bd57ce29350dcfd3eac))
* delete template changelog ([c6018fa](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/c6018faf8b1680a51be7a3d4d362f2f6a0d0e112))
* delete unnecessary files ([39dffe3](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/39dffe315f99c1961852b9248832c6096d6b199c))
* digital twin semantics input is not already semantic ([c5bc8b3](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/c5bc8b3b81df4d37515f2bd0f1f31168fdd5181d))
* get platforms in order ([ee836f6](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/ee836f6a201580e6f8d089c68fcf134205cd7be3))
* improve naming and modeling ([7b27fd8](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/7b27fd8d1b1ba6351f66ef2942c06e1b0018a482))
* include azure dt id directory service ([5f8bb11](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/5f8bb112bce5696e003fb258dfa7dca8d0882a60))
* include shadowing adapter in the engine ([c11be0c](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/c11be0c7d4162c655c57009cfe1e527d26912ec4))
* make http-related function suspendable and pass the necessary data to registration request ([4db075c](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/4db075c10b3d039224b827a98f689b6cefa78fac))
* make relative uri effectively relative ([57f6d7a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/57f6d7af85e0ecacec08c057787546145a431576))
* model digital twin semantics ([1ce3c7e](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/1ce3c7e4a7b396f7476ffd6216bc026cdab70288))
* model DT URI value object ([5f335d5](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/5f335d5af57f3ef5e2a12f67f8904152d7127ea9))
* model dtd manager component ([668e2e3](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/668e2e33fff1f603c1ad6733ffc4e342deb4fdcf))
* model dtkg engine component ([7f0e0c3](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/7f0e0c33f9222eeeda2db737c774c911451e7196))
* model dtkg rdf entities ([8fcc114](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/8fcc114cddfc77fd364312ea52884e9dddff1b12))
* model internal shadowing events ([20f4221](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/20f42219b04f4ce8646ff6fecf34e2f0329477b8))
* model platform management interface component ([7e20b91](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/7e20b9102ca1ad231530841d75c228b6c3079311))
* model shadowing adapter component ([b25901a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/b25901a9055b17ebbb70be3382edab2060337ee2))
* model the adapter http client ([1a3158e](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/1a3158e5f6bf96ef281cccc2c0a7e5da7c1866e0))
* model the concept of dt version ([a68c9e4](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/a68c9e4efd14da631df58fcbd0af078054636101))
* model the configuration of the dynamic part of the adapter ([2d1d715](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/2d1d7159c0defd2f4052e9c0f525f2cd1c8cf022))
* model the dtkg ([884b229](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/884b2290c5268a83c178969ab910218e51fb1070))
* model the platform registration notification body payload ([ca9f952](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/ca9f952fe2a269b7a0798105ee92a3ebe8919506))
* model the raw DT KG ([730a4e2](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/730a4e2b3c4230281b467ffb2f24e78d1de1732a))
* model web server component ([bfb5356](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/bfb5356a7a7b91ec6b25dd4478afeb3b42bcc179))
* pass azure dt id instead of dt uri to the dtd manager and make it suspendable ([e86b755](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/e86b7558083a0f7db49e6705c1d6e279f7293d6b))
* pass configuration to directory service ([8f1cc6a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/8f1cc6a3a4c1e774a80e6e01e3bef9a8bf33ec70))
* pass the execution context to shadowing start ([8dfd7a7](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/8dfd7a7574d2da6b31c84070475bd10a6f180785))
* rename file and move implementation out ([bf34411](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/bf34411e464d8de8669ae8ac304f50735f4a47cf))
* rename file to align with the content ([a4e9134](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/a4e9134fc99662c7bda5fd67d77d45d159f287ee))
* separate azure dt client interface and implementation to be able to create test double ([e1dcffd](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/e1dcffdaa52859e313dbc28c1e90fa10444cc5ac))
* set entrypoint in Dockerfile ([b3e8eb5](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/b3e8eb5523ee327c045e7594f04d94c0d96041de))
* **test:** delete sample test ([0abdb54](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/0abdb541b31c5d925ca05943a6feb0a6113f48fe))
* update copyright header profile ([b060d74](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/b060d748ece321a3e2e910badd755cd240fd2aa8))
* update gitignore to ignore env files ([9ea3e7a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/9ea3e7a9365f18032c5cc99c796c21030afefdb2))
* update gitignore to ignore sample configurations ([c7c77dd](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/c7c77ddaa2cd32cb1e0fcefc39d857aeee9caf47))
* update license ([f0ec663](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/f0ec663a871e2f6300fc775b09ef872599651d57))
* update readme with a template ([429ca72](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/429ca72b2080ad92c1b2cb5b5cdb23c61036f5dc))
* use coroutine scopes and improve usage of coroutine context ([c525121](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/c525121253fac665f7616e79f7a396c2771335f5))
* use DTUri value object to indicate the full dt uri when needed ([1de4570](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/1de4570db3f2de5ad6c7066588c81e6301276e33))
* use DTUri when refer to the concept ([f5a7f59](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/f5a7f5932b2a55e9427cecdeec35774c90f71fa0))

### Style improvements

* inject coroutine context following kotlin style guide ([e6b85c0](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/e6b85c0ce17dcf25e097d610490a3243cbfdc883))
* set plugin version without parenthesis ([f0ed4d7](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/f0ed4d7d04f01a687b77793137cc2929008224a0))
* use kotlin facilities to return empty list when null ([e3474bd](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/e3474bd9a9e31eb972dfc9dd3bc95c491a276a7b))
* use kotlin string facilities ([fe585d1](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/fe585d127b4e78b43fd340fe8ebdc74ffa9852d5))

### Refactoring

* change name to utility that loads dts configuration to represent its responsibility ([12726c7](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/12726c76dcd22ff653a1cd1b1d44622eed36b612))
* move configurations under the same concept and create Configuration interface ([3837203](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/38372033f7c0c117ec0c5309854cb1d55f15943b))
* rename extraction of KG from signalr event ([129f2a9](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/129f2a9ee4e3bc144e49b13551dfb7a88cde1200))
* rename id directory service ([6b76a04](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/6b76a04c6c00a33fce5d67b3e8d23a3c8b846cc9))
* rename package to model broad dt entities ([86c2742](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/86c274255068961a603588c0cf7a7b5b01553d2b))
* rename test to match class to test ([05eae71](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/05eae7116536c0bc81213110ec531d42293ef1c7))
* renaming configuration to differentiate DT configuration from adapter configuration ([dee0bcd](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/dee0bcdce605690e64044c5e15ee3edbafe6ac97))
* **test:** reduce duplicate code ([b475a66](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/b475a6609169a143a3239965b8f52c14bd767d1e))
* use kotlin facilities ([2556b5a](https://github.com/Web-of-Digital-Twins/azuredt-wodt-adapter/commit/2556b5a2b6c95f88bb6f67d41bcf7f3d5a845e35))
