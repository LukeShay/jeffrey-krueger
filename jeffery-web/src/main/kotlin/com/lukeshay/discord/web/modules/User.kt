package com.lukeshay.discord.web.modules

import com.lukeshay.discord.web.extensions.getSession
import com.lukeshay.discord.web.extensions.respondLayout
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.tr

@Suppress("unused") // Referenced in application.conf
fun Application.registerUserRoutes() {
    routing {
        route("/user") {
            get {
                val session = call.getSession()

                if (session.id == "") {
                    call.respondRedirect("/signin")
                } else {
                    call.respondLayout(styleSheets = listOf("table.css")) {
                        div(classes = "table-wrapper") {
                            table {
                                thead {
                                    tr {
                                        th { +"Property" }
                                        th { +"Value" }
                                    }
                                }
                                tbody {
                                    tr {
                                        td { +"Username" }
                                        td { +session.username }
                                    }
                                    tr {
                                        td { +"Discriminator" }
                                        td { +session.discriminator }
                                    }
                                    tr {
                                        td { +"Email" }
                                        td { +"${session.email}" }
                                    }
                                    tr {
                                        td { +"Avatar" }
                                        td { +"${session.avatar}" }
                                    }
                                }
                            }
                            div {
                                a(classes = "btn", href = "/signout") {
                                    +"Sign out"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}