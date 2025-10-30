pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "otusKotlinProject"

includeBuild("lessons")
includeBuild("smart-office-be")
//include("smart-office-be:smart-office-api-v1-jackson")
//findProject(":smart-office-be:smart-office-api-v1-jackson")?.name = "smart-office-api-v1-jackson"
