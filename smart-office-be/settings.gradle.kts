pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
        id("build-docker") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "smart-office-be"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("smart-office-api-log1")
include("smart-office-api-v1-jackson")
include("smart-office-api-v1-mappers")
include("smart-office-app-common")
include("smart-office-app-kafka")
include("smart-office-app-spring")
include("smart-office-biz")
include("smart-office-common")
include("smart-office-repo-common")
include("smart-office-repo-inmemory")
include("smart-office-stubs")
