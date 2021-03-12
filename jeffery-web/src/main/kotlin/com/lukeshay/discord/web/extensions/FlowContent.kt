package com.lukeshay.discord.web.extensions

import kotlinx.html.FlowContent
import kotlinx.html.HtmlTagMarker
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.header
import kotlinx.html.img

@HtmlTagMarker
fun FlowContent.navBar(signedIn: Boolean = false): Unit =
    header {
        div(classes = "navbar") {
            div(classes = "wrapper") {
                a(href = "/", classes = "title") {
                    +"Jeffery Krueger"
                }
                div(classes = "section") {
                    if (signedIn) {
                        a(href = "/user", classes = "item") {
                            img(classes = "icon", src = "/assets/svgs/user.svg", alt = "user")
                        }
                    } else {
                        a(
                            classes = "btn",
                            href = "https://discord.com/api/oauth2/authorize?client_id=${System.getProperty("discord.client.id")}&redirect_uri=http%3A%2F%2Flocalhost%3A8000%2Fsignin%2Fcallback&response_type=code&scope=identify%20email%20guilds", // ktlint-disable max-line-length
                        ) {
                            +"Sign in"
                        }
                    }
                }
            }
        }
    }