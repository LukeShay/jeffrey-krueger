package com.lukeshay.discord.web.modules

import com.lukeshay.discord.sdk.DiscordRequestException
import com.lukeshay.discord.sdk.DiscordSdk
import com.lukeshay.discord.sdk.requests.revokeOAuth2Token
import com.lukeshay.discord.web.domain.Session
import com.lukeshay.discord.web.extensions.getSession
import com.lukeshay.discord.web.extensions.setSession
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import java.net.URLEncoder
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

private val logger: Logger = LogManager.getLogger("com.lukeshay.discord.web.modules.SignOutKt")

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.registerSignOutRoutes() {
    val discordSdk = DiscordSdk(
        System.getProperty("discord.client.id"),
        System.getProperty("discord.client.secret"),
    )

    routing {
        route("/signout") {
            get {
                val session = call.getSession()
                call.setSession(Session())

                call.respondRedirect(
                    if (session.id != "") {
                        try {
                            discordSdk.revokeOAuth2Token(session.toOAuth2TokenRequest())
                            "/"
                        } catch (e: DiscordRequestException) {
                            "/error?errorType=${URLEncoder.encode(e.response.error, "utf-8")}&errorDescription=${URLEncoder.encode(e.response.errorDescription, "utf-8")}" // ktlint-disable max-line-length
                        } catch (e: Exception) {
                            "/error?errorDescription=${URLEncoder.encode(e.message, "utf-8")}"
                        }
                    } else {
                        "/"
                    },
                )
            }
        }
    }
}