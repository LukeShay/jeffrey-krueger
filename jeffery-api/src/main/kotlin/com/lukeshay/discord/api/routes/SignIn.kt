package com.lukeshay.discord.api.routes

import com.lukeshay.discord.api.extensions.getSession
import com.lukeshay.discord.api.extensions.queryParameters
import com.lukeshay.discord.api.extensions.respondJson
import com.lukeshay.discord.api.extensions.setSession
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.signInRouting() {
    val host = "localhost%3A8080"
    val protocol = "http"
    val clientId = "813144218833780748"

    route("/signin") {
        get {
            call.respondRedirect("https://discord.com/oauth2/authorize?client_id=$clientId&scope=identify%20guilds%20email&response_type=code&prompt=none&redirect_uri=$protocol%3A%2F%2F$host%2Fsignin%2Fcallback")
        }
        get("/callback") {
            val code = call.queryParameters()["code"]

            if (code != null) {
                val session = call.getSession()
                call.setSession(session.copy(code = code))
                call.respondJson(Json.encodeToString(call.getSession()))
            } else {
                call.respondText("hey")
            }
        }
    }
}

fun Application.registerSignInRoutes() {
    routing {
        signInRouting()
    }
}