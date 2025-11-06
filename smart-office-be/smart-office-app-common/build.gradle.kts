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
                implementation(projects.smartOfficeCommon)

                // transport models
                implementation(projects.smartOfficeCommon)
                //implementation(projects.smartOfficeApiLog1)
                implementation(projects.smartOfficeApiV1Jackson)

                implementation(projects.smartOfficeBiz)
            }
        }
        test {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

    }
}
