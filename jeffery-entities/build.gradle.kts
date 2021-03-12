plugins {
    id("jeffery.library")
}

group = "com.lukeshay.discord.entities"
version = System.getProperty("app.version", "version")

dependencies {
    api(project(":jeffery-lib"))
}