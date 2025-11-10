plugins {
    id("build-jvm")
}

kotlin {
    sourceSets {
        val coroutinesVersion: String by project
        main {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(libs.coroutines.core)
                implementation(libs.kotlinx.datetime)

                implementation(projects.smartOfficeCommon)

                // transport models
                implementation(projects.smartOfficeCommon)
                implementation(projects.smartOfficeApiLog1)
                implementation(projects.smartOfficeApiV1Jackson)
                implementation(projects.smartOfficeApiV1Mappers)

                implementation(projects.smartOfficeBiz)
            }
        }
        test {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.coroutines.test)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-junit"))
            }
        }

    }
}
