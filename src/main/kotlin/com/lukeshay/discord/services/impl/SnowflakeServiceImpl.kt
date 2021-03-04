package com.lukeshay.discord.services.impl

import com.beust.klaxon.Klaxon
import com.lukeshay.discord.domain.Snowflake
import com.lukeshay.discord.logging.createLogger
import com.lukeshay.discord.services.SnowflakeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class SnowflakeServiceImpl @Autowired constructor(
    private val httpClient: HttpClient,
    private val klaxon: Klaxon,
    private val snowflakeHttpRequest: HttpRequest
) :
    SnowflakeService {
    override fun getSnowflake(): Snowflake {
        try {
            val response =
                httpClient.send(snowflakeHttpRequest, HttpResponse.BodyHandlers.ofString())

            return klaxon.parse<Snowflake>(response.body()) ?: throw Exception("snowflake is null")
        } catch (e: Exception) {
            e.printStackTrace()
            logger.error("there was an error getting a snowflake: $e")
            throw RuntimeException(e)
        }
    }

    override fun getSnowflakeId(): Long {
        return getSnowflake().id
    }

    companion object {
        private val logger = createLogger(SnowflakeServiceImpl::class.java)
    }
}
