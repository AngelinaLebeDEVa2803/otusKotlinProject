plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.smartOfficeApiV1Jackson)
    implementation(projects.smartOfficeCommon)
    implementation(libs.kotlinx.datetime)

    testImplementation(kotlin("test-junit"))
    testImplementation(projects.smartOfficeStubs)
}
