import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

val kotlinVersion = "1.4.31"
val springVersion = "5.3.4"
val hibernateVersion = "6.0.0.Alpha6"
val junit5Version = "5.6.0"

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.4.31")
    }
}

plugins {
    kotlin("jvm") version "1.4.31"
    application
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("org.jetbrains.kotlin.plugin.spring") version "1.4.31"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.4.31"
    id("com.palantir.git-version") version "0.12.3"
}

group = "com.lukeshay.discord"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    // Discord dependencies
    implementation("net.dv8tion:JDA:4.2.0_228") {
        exclude("opus-java")
    }

    // Spring dependencies
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-beans:$springVersion")
    implementation("org.springframework.data:spring-data-jpa:2.4.5")

    // Hibernate dependencies
    implementation("org.hibernate.orm:hibernate-core:$hibernateVersion")

    // Postgres dependencies
    implementation("org.postgresql:postgresql:42.2.19")

    // Kotlin dependencies
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    // Test dependencies
    // JUnit5 dependencies
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit5Version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit5Version")

    // Hibernate dependencies
    testImplementation("org.hibernate.orm:hibernate-testing:$hibernateVersion")

    // H2 dependencies
    implementation("com.h2database:h2:1.4.200")

    // Mockito dependencies
    testImplementation("org.mockito:mockito-core:2.+")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClassName = "com.lukeshay.discord.MainKt"
}

tasks.distTar {
    archiveFileName.set("discord-bot.tar")
}

tasks.jar {
    try {
        val details = versionDetails()
        File("src/main/resources/application.properties").writeText("commit=${details.gitHash}")
    } catch (e: Exception) {
    }
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/style-violations.kt")
    }
}
