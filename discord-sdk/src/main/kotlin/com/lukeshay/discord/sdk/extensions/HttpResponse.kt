package com.lukeshay.discord.sdk.extensions

import io.ktor.client.statement.HttpResponse
import io.ktor.util.toByteArray
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

suspend fun HttpResponse.contentAsString(): String = String(content.toByteArray(), Charsets.UTF_8)

suspend fun HttpResponse.json(): JsonElement =
    Json.parseToJsonElementIgnoreUnknown(contentAsString())