package com.lukeshay.discord.web.modules

import com.lukeshay.discord.entities.GuildConfigs
import com.lukeshay.discord.sdk.DiscordSdk
import com.lukeshay.discord.sdk.requests.getMyGuildsOrEmpty
import com.lukeshay.discord.web.extensions.getSession
import com.lukeshay.discord.web.extensions.respondLayout
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.sentry.Sentry
import kotlinx.html.button
import kotlinx.html.h1
import kotlinx.html.img
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.tr
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.sql.transactions.transaction

private val logger = LogManager.getLogger("com.lukeshay.discord.web.modules.HomeKt")

@Suppress("unused")
fun Application.registerHomeRoutes() {
    val discordSdk = DiscordSdk(
        System.getProperty("discord.client.id"),
        System.getProperty("discord.client.secret"),
    )

    routing {
        route("/") {
            get {
                val session = call.getSession()

                if (session.id != "") {
                    val guilds = transaction {
                        GuildConfigs.selectAllByAdminId(session.id.toLong())
                    }
                    val myGuilds = discordSdk.getMyGuildsOrEmpty(session.toOAuth2TokenRequest())

                    val discordGuilds = guilds.mapNotNull {
                        try {
                            myGuilds.first { mg -> "${it.id}" == mg.id }
                        } catch (e: Exception) {
                            Sentry.captureException(e)
                            logger.error("error getting guild ${e.message}")
                            null
                        }
                    }

                    call.respondLayout(styleSheets = listOf("table.css")) {
                        table {
                            thead {
                                tr {
                                    th { +"Guild Name" }
                                    th { +"Guild Icon" }
                                    th { +"Commands" }
                                    th { +"Daily Greeting" }
                                    th { +"Daily Quote" }
                                    th { +"Edit" }
                                }
                            }
                            tbody {
                                guilds.map {
                                    discordGuilds.firstOrNull { mg -> "${it.id}" == mg.id }
                                        ?.let { discordGuild ->
                                            tr {
                                                td { +discordGuild.name }
                                                td {
                                                    img(
                                                        src = "https://cdn.discordapp.com/icons/${it.id}/${discordGuild.icon}.png", // ktlint-disable max-line-length
                                                        alt = "icon",
                                                    )
                                                }
                                                td { +"${it.commands}" }
                                                td { +"${it.dailyGreeting}" }
                                                td { +"${it.dailyQuote}" }
                                                td {
                                                    button(classes = "btn") { +"Edit" }
                                                }
                                            }
                                        }
                                }
                            }
                        }
                    }
                } else {
                    call.respondLayout {
                        h1 { +"Hey There" }
                    }
                }
            }
        }
    }
}