import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

val kotlinVersion = "1.4.31"
val springVersion = "5.3.4"
val hibernateVersion = "6.0.0.Alpha6"
val junit5Version = "5.6.0"

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
    id("com.heroku.sdk.heroku-gradle") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.lukeshay.discord"
version = System.getProperty("app.version", "version")

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
    implementation("com.mchange:c3p0:0.9.5.5")

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

    // Spring dependencies
    testImplementation("org.springframework:spring-test:$springVersion")
}

// tasks.jar {
//    manifest {
//        attributes(mapOf("Main-Class" to mainClass))
//    }
// }

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks.shadowJar {
    minimize {
        exclude(dependency(".*:.*:.*"))
        include(dependency("net.dv8tion:.*:.*"))
        include(dependency("org.postgresql:postgresql:.*"))
        include(dependency("org.hibernate.orm:hibernate-core:.*"))
        include(dependency("com.mchange:c3p0:.*"))
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClassName = "com.lukeshay.discord.MainKt"
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

heroku {
    appName = System.getProperty("heroku.app.name", "jeffery-krueger-dev")
    jdkVersion = "11"
    processTypes = mapOf("worker" to "java \$JAVA_OPTS -jar build/libs/discord-bot-$version-all.jar")
    buildpacks = listOf("heroku/jvm")
    includes = listOf("build/libs/discord-bot-$version-all.jar")
    isIncludeBuildDir = false
}
