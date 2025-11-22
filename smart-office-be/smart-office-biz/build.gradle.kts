plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.datetime)
    implementation(projects.smartOfficeStubs)
    implementation(projects.smartOfficeCommon)
    implementation(projects.smartOfficeRepoTests)
    implementation(projects.smartOfficeRepoInmemory)

    api("ru.otus.otuskotlin.smartoffice.libs:smart-office-lib-cor")

    testImplementation(kotlin("test-junit"))
    testImplementation(projects.smartOfficeStubs)
    api(libs.coroutines.test)
}

