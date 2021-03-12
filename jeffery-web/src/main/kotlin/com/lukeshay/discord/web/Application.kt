package com.lukeshay.discord.web

import com.lukeshay.discord.bot.enums.Environment
import com.lukeshay.discord.entities.setupExposed
import com.lukeshay.discord.utils.DataAccessUtils
import com.lukeshay.discord.utils.LoggingUtils
import com.lukeshay.discord.utils.SecretUtils
import com.lukeshay.discord.web.domain.Session
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.serialization.json
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import kotlin.collections.set
import kotlinx.serialization.json.Json

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

/**
 * Please note that you can use any other name instead of *module*.
 * Also note that you can have more then one modules in your application.
 * */
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val env = Environment.determineEnvironment()
    SecretUtils.loadSecrets(
        listOf(
            "discord.token",
            "discord.client.id",
            "discord.client.secret",
            "sentry.dsn",
        ),
        environment = env,
    )
    LoggingUtils.setupLogger(env)
    setupExposed(DataAccessUtils.dataSource())

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        method(HttpMethod.Post)
        method(HttpMethod.Get)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
    install(Sessions) {
        cookie<Session>("SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
    install(StatusPages) {
        exception<AuthenticationException> {
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> {
            call.respond(HttpStatusCode.Forbidden)
        }
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            },
        )
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()