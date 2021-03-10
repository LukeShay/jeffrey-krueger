import com.lukeshay.discord.gradle.passSystemProperties
import com.lukeshay.discord.gradle.setupHeroku
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("jeffery.application")
    id("com.heroku.sdk.heroku-gradle") version "2.0.0" apply true
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0" apply true
    id("com.github.johnrengelman.shadow") version "6.1.0" apply true
}

dependencies {
    implementation(project(":jeffery-entities"))
    implementation(project(":jeffery-lib"))
}

tasks.runShadow {
    passSystemProperties(this)
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
    }
    ignoreFailures.set(true)
}

application {
    mainClass.set("com.lukeshay.discord.bot.MainKt")
    mainClassName = "com.lukeshay.discord.bot.MainKt"
}

heroku {
    val props = setupHeroku("jeffery-bot", version.toString(), "jeffery-krueger-dev", "worker")
    appName = props.appName
    jdkVersion = props.jdkVersion
    processTypes = props.processTypes
    buildpacks = props.buildpacks
    includes = props.includes
    isIncludeBuildDir = props.isIncludeBuildDir
}