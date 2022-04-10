/*
 *  Copyright (c) 2022 Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Bayerische Motoren Werke Aktiengesellschaft (BMW AG) - initial API and implementation
 *
 */

plugins {
    `java-library`
    id("application")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

dependencies {
    api(project(":core"))
    api(project(":data-protocols:ids"))
    api(project(":extensions:http"))
    api(project(":extensions:iam:iam-mock"))
    api(project(":extensions:api:control"))

    api(project(":extensions:filesystem:configuration-fs"))
    api(project(":extensions:filesystem:vault-fs"))

    api(project(":extensions:in-memory:assetindex-memory"))
    api(project(":extensions:in-memory:transfer-store-memory"))
    api(project(":extensions:in-memory:negotiation-store-memory"))
    api(project(":extensions:in-memory:contractdefinition-store-memory"))
    api(project(":extensions:in-memory:policy-store-memory"))

    api(project(":extensions:api:data-management:api-configuration"))
    api(project(":extensions:api:data-management:asset"))
    api(project(":extensions:api:auth-tokenbased"))

    api(project(":extensions:data-plane-transfer:data-plane-transfer-spi"))
    api(project(":extensions:data-plane-transfer:data-plane-transfer-core"))
    api(project(":extensions:data-plane-transfer:data-plane-transfer-sync"))
    api(project(":extensions:http-receiver"))
    api(project(":common:token-generation-lib"))
}

application {
    mainClass.set("org.eclipse.dataspaceconnector.boot.system.runtime.BaseRuntime")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    exclude("**/pom.properties", "**/pom.xm")
    mergeServiceFiles()
    archiveFileName.set("provider-control-plane.jar")
}