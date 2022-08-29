import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.6.0"
val kotlinxCoroutinesVersion = "1.5.2"
val kotestVersion = "4.6.4"
val kotestSpringVersion = "1.0.1"
val mockkVersion = "1.12.1"
val postgresVersion = "42.3.1"
val springBootVersion = "2.5.6"
val springCloudGCPVersion = "1.2.8.RELEASE"
val awaitilityVersion = "4.1.1"
val mockwebserverVersion = "4.9.3"
val flywayVersion = "8.1.0"
val log4jVersion = "2.17.0"

val postgresHOST = System.getenv("POSTGRES_HOST")
val postgresPORT = System.getenv("POSTGRES_PORT")
val postgresDB = System.getenv("POSTGRES_DB")

plugins {
    kotlin("jvm") version "1.6.0"

    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"

    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    id("org.flywaydb.flyway") version "8.1.0"

    jacoco
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.apache.logging.log4j") {
            useVersion("$log4jVersion")
        }
    }
}

repositories {
    mavenCentral()
}

flyway {
    url = "jdbc:postgresql://$postgresHOST:$postgresPORT/$postgresDB"
    user = System.getenv("POSTGRES_USER")
    password = System.getenv("POSTGRES_PASS")
}

sourceSets.create("test-e2e") {
    java.srcDir("src/test-e2e/kotlin")
    resources.srcDir("src/test-e2e/resources")
    compileClasspath += sourceSets["main"].output
    runtimeClasspath += sourceSets["main"].output
}

val testE2eImplementation = configurations["testE2eImplementation"].extendsFrom(configurations.testImplementation.get())
val testE2eRuntimeOnly = configurations["testE2eRuntimeOnly"].extendsFrom(configurations.testRuntimeOnly.get())

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$kotlinxCoroutinesVersion")
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(platform("org.springframework.cloud:spring-cloud-gcp-dependencies:$springCloudGCPVersion"))
    implementation("org.springframework.cloud:spring-cloud-gcp-starter-pubsub")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("org.awaitility:awaitility-kotlin:$awaitilityVersion")

    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    testE2eImplementation("com.squareup.okhttp3:okhttp:$mockwebserverVersion")
    testE2eImplementation("com.squareup.okhttp3:mockwebserver:$mockwebserverVersion")

    testE2eImplementation("org.flywaydb:flyway-core:$flywayVersion")
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val startDockerTask = task<Exec>("start-docker") {
    commandLine("docker", "compose", "-f", "docker-compose.e2e.yml", "up", "-d")
}

val stopDockerTask = task<Exec>("stop-docker") {
    commandLine("docker", "compose", "-f", "docker-compose.e2e.yml", "down")
}

val testE2eTask = task<Test>("test-e2e") {
    dependsOn(startDockerTask)
    finalizedBy(stopDockerTask)
    description = "End-to-end tests"
    group = "verification"
    testClassesDirs = sourceSets["test-e2e"].output.classesDirs
    classpath = sourceSets["test-e2e"].runtimeClasspath
}

tasks.check {
    dependsOn(testE2eTask)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test, testE2eTask)

    executionData.setFrom(fileTree(buildDir).include("/jacoco/*.exec"))

    reports {
        xml.required.set(true)
    }
}

springBoot {
    mainClass.set("br.com.hash.clearing.ClearingApplicationKt")
}
