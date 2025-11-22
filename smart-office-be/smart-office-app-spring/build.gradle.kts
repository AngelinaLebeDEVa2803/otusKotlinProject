plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencies)
    alias(libs.plugins.spring.kotlin)
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
}

dependencies {
    implementation(libs.spring.actuator)
    implementation(libs.spring.webflux)
    implementation(libs.spring.webflux.ui)
    implementation(libs.jackson.kotlin)
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.reactor)
    implementation(libs.coroutines.reactive)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    // Внутренние модели
    implementation(projects.smartOfficeAppCommon)
    implementation(projects.smartOfficeCommon)

    // log lib
    implementation("ru.otus.otuskotlin.smartoffice.libs:smart-office-lib-logging-logback")

    // v1 api
    implementation(projects.smartOfficeApiV1Jackson)
    implementation(projects.smartOfficeApiV1Mappers)

    // biz
    implementation(projects.smartOfficeBiz)

    // DB
    implementation(projects.smartOfficeRepoStubs)
    implementation(projects.smartOfficeRepoInmemory)
    testImplementation(projects.smartOfficeRepoCommon)
    testImplementation(projects.smartOfficeStubs)

    // tests
    testImplementation(kotlin("test-junit"))
    testImplementation(libs.spring.test)
    //testImplementation(libs.mockito.kotlin)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(libs.spring.mockk)
}

tasks {
    withType<ProcessResources> {
        val files = listOf("spec-v1").map {
            rootProject.ext[it]
        }
        from(files) {
            into("/static")
            filter {
                // Устанавливаем версию в сваггере
                it.replace("\${VERSION_APP}", project.version.toString())
            }

        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
