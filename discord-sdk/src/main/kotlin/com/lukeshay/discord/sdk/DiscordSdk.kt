package com.lukeshay.discord.sdk

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

class DiscordSdk(
    val clientId: String,
    val clientSecret: String,
    val httpClient: HttpClient = HttpClient(CIO),
    val discordApiUrl: String = "https://discord.com/api/v8"
)