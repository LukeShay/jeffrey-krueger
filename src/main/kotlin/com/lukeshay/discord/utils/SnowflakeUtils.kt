package com.lukeshay.discord.utils

import com.beust.klaxon.Klaxon
import com.lukeshay.discord.domain.Snowflake
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun getSnowflakeId(): Long {
    val httpClient = HttpClient.newBuilder().build()
    val builder = HttpRequest.newBuilder().uri(URI.create(System.getProperty("snowflake.url")))

    try {
        builder.header("X-Client-Secret", System.getProperty("snowflake.client.secret"))
    } catch (e: NullPointerException) {
    }

    val httpRequest = builder.build()

    val response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

    return Klaxon().parse<Snowflake>(response.body())?.id ?: throw Exception("snowflake is null")
}
