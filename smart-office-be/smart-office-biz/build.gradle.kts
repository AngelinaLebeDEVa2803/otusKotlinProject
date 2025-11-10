plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.smartOfficeStubs)
    implementation(projects.smartOfficeCommon)

    testImplementation(kotlin("test-junit"))
    testImplementation(projects.smartOfficeStubs)
}

