import com.lukeshay.discord.gradle.passSystemProperties
import com.lukeshay.discord.gradle.setupHeroku

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    id("jeffery.application") apply true
    id("com.heroku.sdk.heroku-gradle") version "2.0.0" apply true
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0" apply true
    id("com.github.johnrengelman.shadow") version "6.1.0" apply true
    kotlin("plugin.serialization") version "1.4.30" apply true
}

group = "com.lukeshay.discord.web"

repositories {
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation(project(":jeffery-entities"))
    implementation(project(":jeffery-lib"))

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

tasks.runShadow {
    passSystemProperties(this)
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    ignoreFailures.set(true)
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
    mainClass.set("io.ktor.server.netty.EngineMain")
}

heroku {
    val props = setupHeroku("jeffery-web", version.toString(), "jk-web-dev", "web")
    appName = props.appName
    jdkVersion = props.jdkVersion
    processTypes = props.processTypes
    buildpacks = props.buildpacks
    includes = props.includes
    isIncludeBuildDir = props.isIncludeBuildDir
}
