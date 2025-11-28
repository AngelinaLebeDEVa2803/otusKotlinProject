plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.smartOfficeCommon)
    implementation(libs.coroutines.core)


    testImplementation(kotlin("test-junit"))
    testImplementation(projects.smartOfficeStubs)
    api(libs.coroutines.test)
}

