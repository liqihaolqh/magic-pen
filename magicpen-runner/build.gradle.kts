plugins {
    kotlin("jvm") version "1.4.31"
}

group = "com.github.magicpen"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":magicpen-api"))
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(platform("io.vertx:vertx-stack-depchain:4.0.2"))
    implementation("io.vertx:vertx-lang-kotlin")
    implementation("io.vertx:vertx-lang-kotlin-coroutines")

    testImplementation("io.vertx:vertx-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
        )
    }
}