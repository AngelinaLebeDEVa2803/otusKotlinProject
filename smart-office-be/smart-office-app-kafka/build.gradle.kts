plugins {
    application
    id("build-jvm")
    alias(libs.plugins.shadowJar)
    id("build-docker")
}

application {
    mainClass.set("ru.otus.otuskotlin.smartoffice.app.kafka.MainKt")
}

docker {
    imageName = "ok-smartoffice-app-kafka"
}

dependencies {
    implementation(libs.kafka.client)
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.atomicfu)

    implementation("ru.otus.otuskotlin.smartoffice.libs:smart-office-lib-logging-logback")

    implementation(project(":smart-office-app-common"))

    // transport models
    implementation(project(":smart-office-common"))
    implementation(project(":smart-office-api-v1-jackson"))
    implementation(project(":smart-office-api-v1-mappers"))
    // logic
    implementation(project(":smart-office-biz"))
    implementation(project(":smart-office-stubs"))

    testImplementation(kotlin("test-junit"))
}

tasks {
    shadowJar {
        manifest {
            attributes(mapOf("Main-Class" to application.mainClass.get()))
        }
    }

    dockerBuild {
        dependsOn("shadowJar")
    }
}

