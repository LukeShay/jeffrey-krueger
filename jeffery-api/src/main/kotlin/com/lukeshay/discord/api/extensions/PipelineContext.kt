package com.lukeshay.discord.api.extensions

import com.lukeshay.discord.api.domain.Session
import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.content.OutgoingContent
import io.ktor.response.respondText
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set

fun ApplicationCall.getSession(): Session =
    sessions.get<Session>() ?: Session()

fun ApplicationCall.setSession(session: Session): Unit =
    sessions.set(session)

fun ApplicationCall.queryParameters(): Parameters =
    request.queryParameters

suspend fun ApplicationCall.respondJson(
    body: String,
    status: HttpStatusCode? = null,
    configure: OutgoingContent.() -> Unit = {}
) {
    respondText(body, ContentType.Application.Json, status, configure)
}