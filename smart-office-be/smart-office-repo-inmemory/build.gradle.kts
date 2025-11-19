plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.smartOfficeCommon)
    implementation(projects.smartOfficeRepoCommon)
    implementation(libs.kotlinx.datetime)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.core)
    implementation(libs.db.cache4k)
    implementation(libs.uuid)


    testImplementation(kotlin("test-junit"))
    testImplementation(projects.smartOfficeStubs)
    api(libs.coroutines.test)
}
