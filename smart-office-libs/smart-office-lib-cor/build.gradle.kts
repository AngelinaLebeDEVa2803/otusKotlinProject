plugins {
    id("build-jvm")
}

kotlin {
    sourceSets {
        main {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(libs.coroutines.core)
            }
        }
        test {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.coroutines.test)
            }
        }
    }
}
