plugins {
    kotlin("jvm") apply false
}

group = "ru.otus.otuskotlin.education"
version = "1.0-SNAPSHOT"

subprojects {
    repositories {
        mavenCentral()
    }
    group = rootProject.group
    version = rootProject.version
}
