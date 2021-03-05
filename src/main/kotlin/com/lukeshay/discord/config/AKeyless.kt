package com.lukeshay.discord.config

import com.lukeshay.discord.enums.Environment
import io.akeyless.client.ApiClient
import io.akeyless.client.api.V2Api
import io.akeyless.client.model.Configure
import io.akeyless.client.model.GetSecretValue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AKeyless {
    private val aKeylessAccessId = System.getProperty("akeyless.access.id") ?: throw Exception("akeyless.access.id not found")
    private val aKeylessAccessKey = System.getProperty("akeyless.access.key") ?: throw Exception("akeyless.access.key not found")

    @Bean
    fun apiClient(): ApiClient {
        val client = io.akeyless.client.Configuration.getDefaultApiClient()

        client.basePath = "https://api.akeyless.io"

        return client
    }

    @Bean
    fun v2Api(apiClient: ApiClient): V2Api {
        return V2Api(apiClient)
    }

    @Bean
    fun aKeylessToken(apiClient: ApiClient, v2Api: V2Api): String {
        return v2Api.configure(
            Configure().accessId(aKeylessAccessId).accessKey(aKeylessAccessKey)
        ).token
            ?: throw Exception("error getting v2 api token")
    }

    @Bean
    fun secretsPath(): String {
        return "jeffery-krueger/${Environment.determineEnv().toString().toLowerCase()}"
    }

    @Bean
    fun secrets(v2Api: V2Api, aKeylessToken: String, secretsPath: String): Secrets {
        val body = GetSecretValue().addNamesItem("$secretsPath/discord-token")
            .addNamesItem("$secretsPath/snowflake-url")
            .addNamesItem("$secretsPath/snowflake-client-secret")
            .token(aKeylessToken)

        val result = v2Api.getSecretValue(body)

        val discordToken = result["$secretsPath/discord-token"]
            ?: throw Exception("discord token secret not found")
        val snowflakeUrl = result["$secretsPath/snowflake-url"]
            ?: throw Exception("discord token secret not found")
        val snowflakeClientSecret = result["$secretsPath/snowflake-client-secret"]
            ?: throw Exception("discord token secret not found")

        return Secrets(discordToken, snowflakeUrl, snowflakeClientSecret)
    }
}
