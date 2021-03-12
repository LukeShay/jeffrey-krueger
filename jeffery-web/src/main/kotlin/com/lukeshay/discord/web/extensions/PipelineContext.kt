package com.lukeshay.discord.web.extensions

import com.lukeshay.discord.web.domain.Session
import io.ktor.application.ApplicationCall
import io.ktor.html.respondHtml
import io.ktor.http.Parameters
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.main
import kotlinx.html.title

fun ApplicationCall.getSession(): Session = sessions.get<Session>() ?: Session()

fun ApplicationCall.setSession(session: Session): Unit = sessions.set(session)

fun ApplicationCall.queryParameters(): Parameters = request.queryParameters

fun ApplicationCall.getQueryParam(key: String, default: String? = null): String? {
    return try {
        request.queryParameters[key]
    } catch (e: Throwable) {
        default
    }
}

suspend fun ApplicationCall.respondLayout(
    title: String = "Jeffery Krueger",
    styleSheets: List<String> = listOf(),
    body: FlowContent.() -> Unit
) {
    val session = getSession()

    respondHtml {
        head {
            title { +title }
            link(rel = "stylesheet", href = "/assets/css/reset.css", type = "text/css")
            link(rel = "stylesheet", href = "/assets/css/style.css", type = "text/css")
            link(rel = "stylesheet", href = "/assets/css/navbar.css", type = "text/css")
            link(rel = "stylesheet", href = "/assets/css/layout.css", type = "text/css")

            styleSheets.map {
                link(
                    rel = "stylesheet",
                    href = "/assets/css/$it",
                    type = "text/css",
                )
            }
        }
        body {
            navBar(session.id != "")
            main {
                div(classes = "content") {
                    body()
                }
            }
        }
    }
}