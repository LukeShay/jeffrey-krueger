package com.lukeshay.discord.web.modules

import io.ktor.application.Application
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing

@Suppress("unused")
fun Application.registerAssetsRoutes() {
    routing {
        static("assets") {
            resources("assets")
        }
    }
}