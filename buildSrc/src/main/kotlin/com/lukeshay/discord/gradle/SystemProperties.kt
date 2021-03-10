package com.lukeshay.discord.gradle

import org.gradle.api.Project
import org.gradle.process.JavaForkOptions

fun Project.passSystemProperties(jfo: JavaForkOptions) {
    jfo.systemProperty("database.url", System.getProperty("database.url"))
    jfo.systemProperty("environment", System.getProperty("environment"))
    jfo.systemProperty("akeyless.access.id", System.getProperty("akeyless.access.id"))
    jfo.systemProperty("akeyless.access.key", System.getProperty("akeyless.access.key"))
}

fun Project.projectVersion(): String = System.getProperty("app.version", "version")