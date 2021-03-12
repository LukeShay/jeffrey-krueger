val ktorVersion: String by project
val kotlinxSerializationVersion: String by project

plugins {
    id("jeffery.library")
    kotlin("plugin.serialization") version "1.4.31" apply true
}

group = "com.lukeshay.discord.sdk"
version = System.getProperty("app.version", "version")

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
}
