plugins {
    id("build-jvm")
}

kotlin {
    sourceSets {
        val coroutinesVersion: String by project
        main {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(libs.kotlinx.datetime)
                api("ru.otus.otuskotlin.smartoffice.libs:smart-office-lib-logging-common")
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