package com.lukeshay.discord.gradle

import org.gradle.api.Project

class HerokuProps(
    project: String,
    version: String,
    defaultAppName: String,
    processType: String,
    programArguments: String = ""
) {
    val appName: String = System.getProperty("heroku.app.name", defaultAppName)
    val jdkVersion = "11"
    val processTypes = mapOf(
        processType to "java \$JAVA_OPTS " +
            "-Ddatabase.url=\$DATABASE_URL " +
            "-Denvironment=\$ENVIRONMENT " +
            "-Dakeyless.access.id=\$AKEYLESS_ACCESS_ID " +
            "-Dakeyless.access.key=\$AKEYLESS_ACCESS_KEY " +
            "-Dapp.version=${System.getProperty("app.version")}" +
            "-jar $project/build/libs/$project-$version-all.jar $programArguments"
    )
    val buildpacks = listOf("heroku/jvm")
    val includes = listOf("$project/build/libs/$project-$version-all.jar")
    val isIncludeBuildDir = false
}

fun Project.setupHeroku(
    project: String,
    version: String,
    defaultAppName: String,
    processType: String,
    programArguments: String = ""
): HerokuProps =
    HerokuProps(project, version, defaultAppName, processType, programArguments)