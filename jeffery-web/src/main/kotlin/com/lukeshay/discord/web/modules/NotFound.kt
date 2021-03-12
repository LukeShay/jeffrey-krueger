package com.lukeshay.discord.web.modules

import com.lukeshay.discord.web.extensions.respondLayout
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import kotlinx.html.h1

@Suppress("unused")
fun Application.registerNotFoundRoutes() {
    routing {
        route("/*") {
            get {
                call.respondLayout {
                    h1 { +"Uh-oh, it looks like this page does not exist" }
                }
            }
        }
    }
}