plugins {
    id("build-jvm")
}
repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(projects.smartOfficeCommon)
    api(projects.smartOfficeRepoCommon)

    implementation(libs.coroutines.core)
    implementation(libs.uuid)

    implementation(libs.db.postgres)
    implementation(libs.bundles.exposed)

    testImplementation(kotlin("test-junit"))
    testImplementation(projects.smartOfficeRepoTests)
    testImplementation(libs.testcontainers.core)
    testImplementation(libs.logback)

}