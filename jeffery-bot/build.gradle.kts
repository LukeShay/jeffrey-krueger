import com.lukeshay.discord.gradle.passSystemProperties
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("jeffery-krueger.kotlin-application-conventions")
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("com.heroku.sdk.heroku-gradle") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

version = System.getProperty("app.version", "version")

dependencies {
    // Discord dependencies
    implementation("net.dv8tion:JDA:4.2.0_228") {
        exclude("opus-java")
    }

    // klaxon dependencies
    implementation("com.beust:klaxon:5.0.1")

    // AKEYLESS dependencies
    implementation("io.akeyless:akeyless-java:2.2.1")

    // Sentry dependencies
    implementation("io.sentry:sentry-log4j2:4.3.0")
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
    mainClass.set("com.lukeshay.discord.MainKt")
    mainClassName = "com.lukeshay.discord.MainKt"
}

heroku {
    appName = System.getProperty("heroku.app.name", "jeffery-krueger-dev")
    jdkVersion = "11"
    processTypes =
        mapOf(
            "worker" to "java \$JAVA_OPTS " +
                "-Ddatabase.url=\$DATABASE_URL " +
                "-Denvironment=\$ENVIRONMENT " +
                "-Dakeyless.access.id=\$AKEYLESS_ACCESS_ID " +
                "-Dakeyless.access.key=\$AKEYLESS_ACCESS_KEY " +
                "-jar build/libs/jeffery-krueger-$version-all.jar"
        )
    buildpacks = listOf("heroku/jvm")
    includes = listOf("build/libs/jeffery-krueger-$version-all.jar")
    isIncludeBuildDir = false
}