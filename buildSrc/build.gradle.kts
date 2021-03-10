plugins {
    `kotlin-dsl` apply true
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
    gradleApi()
}