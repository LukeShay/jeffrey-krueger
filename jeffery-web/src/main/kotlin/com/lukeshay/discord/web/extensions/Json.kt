package com.lukeshay.discord.web.extensions

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

inline fun <reified T> Json.decodeFromJsonElementIgnoreUnknown(json: JsonElement): T =
    Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
    }.decodeFromJsonElement(serializersModule.serializer(), json)

fun Json.parseToJsonElementIgnoreUnknown(json: String): JsonElement =
    Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
    }.parseToJsonElement(json)