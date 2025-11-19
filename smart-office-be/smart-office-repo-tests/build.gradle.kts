plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.smartOfficeCommon)
    implementation(projects.smartOfficeStubs)
    implementation(projects.smartOfficeRepoStubs)
    implementation(libs.kotlinx.datetime)

    implementation(libs.coroutines.core)

    testImplementation(kotlin("test-junit"))
    api(libs.coroutines.test)
}
