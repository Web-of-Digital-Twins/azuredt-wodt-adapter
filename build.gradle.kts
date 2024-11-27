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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    application
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.qa)
    alias(libs.plugins.kotlin.serialization)
}

group = "io.github.wodt"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.azure.digital.twins)
    implementation(libs.azure.identity)
    implementation(libs.azure.signalr)
    implementation(libs.ditto.wot.model)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.scripting)
    implementation(libs.logging)
    implementation(libs.sl4j)
    testImplementation(libs.bundles.kotlin.testing)
}

tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    compilerOptions {
        allWarningsAsErrors = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        showCauses = true
        showStackTraces = true
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

application {
    mainClass.set("AppKt")
}
