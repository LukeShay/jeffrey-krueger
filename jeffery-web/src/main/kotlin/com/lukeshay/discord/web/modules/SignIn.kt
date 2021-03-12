package com.lukeshay.discord.web.modules

import com.lukeshay.discord.sdk.DiscordRequestException
import com.lukeshay.discord.sdk.DiscordSdk
import com.lukeshay.discord.sdk.domain.DiscordError
import com.lukeshay.discord.sdk.requests.getOAuth2Token
import com.lukeshay.discord.sdk.requests.getUsersMe
import com.lukeshay.discord.web.domain.Session
import com.lukeshay.discord.web.extensions.queryParameters
import com.lukeshay.discord.web.extensions.setSession
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import io.sentry.Sentry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

private val logger: Logger = LogManager.getLogger("com.lukeshay.discord.web.modules.SignInKt")

private suspend fun makeUserRequest(discordSdk: DiscordSdk, code: String): Any {
    logger.debug("sending oauth2 token request for code $code")

    val oAuth2TokenResponse = try {
        discordSdk.getOAuth2Token(
            "authorization_code",
            code,
            "identify guilds email",
            "http://localhost:8000/signin/callback",
        )
    } catch (e: DiscordRequestException) {
        Sentry.captureException(e)
        logger.warn("error getting oauth2 token: ${e.message}")
        return e.response
    }

    logger.debug("sending user request with session $oAuth2TokenResponse")

    return try {
        val user = discordSdk.getUsersMe(oAuth2TokenResponse)

        logger.debug("user response from discord: $user")

        Session.fromOAuth2AndUser(oAuth2TokenResponse, user)
    } catch (e: DiscordRequestException) {
        Sentry.captureException(e)
        logger.warn("error getting oauth2 token: ${e.message}")
        e.response
    }
}

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
fun Application.registerSignInRoutes() {
    val discordSdk = DiscordSdk(
        System.getProperty("discord.client.id"),
        System.getProperty("discord.client.secret"),
    )

    routing {
        route("/signin") {
            get("/callback") {
                val code = call.queryParameters()["code"]

                call.respondRedirect(
                    if (code != null) {
                        when (val response = makeUserRequest(discordSdk, code)) {
                            is Session -> {
                                call.setSession(response)
                                "/user"
                            }
                            is DiscordError ->
                                "/error?errorType=${response.error}&description=${response.errorDescription}" // ktlint-disable max-line-length
                            else -> "/error?errorType=unknown"
                        }
                    } else {
                        "/error?errorType=no-code"
                    },
                )
            }
        }
    }
}