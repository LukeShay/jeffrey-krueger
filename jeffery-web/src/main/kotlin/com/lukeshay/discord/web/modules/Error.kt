package com.lukeshay.discord.web.modules

import com.lukeshay.discord.web.extensions.getQueryParam
import com.lukeshay.discord.web.extensions.respondLayout
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.p
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger("com.lukeshay.discord.web.modules.ErrorKt")

@Suppress("unused") // Referenced in application.conf
fun Application.registerErrorRoutes() {
    routing {
        route("/error") {
            get {
                val errorType = call.getQueryParam("errorType")
                val errorDescription = call.getQueryParam("errorDescription")

                logger.info("error page visited, type: $errorType, description: $errorDescription")

                call.respondLayout(styleSheets = listOf("error.css")) {
                    div {
                        h1 { +"Looks like we encountered an error" }
                        h2 { +"Please try again or contact support." }
                        if (errorType != null) {
                            p { +"Error type: $errorType" }
                        }
                        if (errorDescription != null) {
                            p { +"Error description: $errorDescription" }
                        }
                    }
                }
            }
        }
    }
}