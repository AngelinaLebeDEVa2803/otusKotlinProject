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


    api("ru.otus.otuskotlin.smartoffice.libs:smart-office-lib-cor")

    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(projects.smartOfficeRepoTests)
    testImplementation(projects.smartOfficeRepoInmemory)
    testImplementation(projects.smartOfficeStubs)
    api(libs.coroutines.test)
}

