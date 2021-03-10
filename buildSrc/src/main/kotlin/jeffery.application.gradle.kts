plugins {
    id("jeffery.common")
    application
}

dependencies {
    // AKEYLESS dependencies
    implementation("io.akeyless:akeyless-java:2.2.1")

    // Sentry dependencies
    implementation("io.sentry:sentry-log4j2:4.3.0")
}