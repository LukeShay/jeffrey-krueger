package com.lukeshay.discord

import com.lukeshay.discord.config.Bot
import com.lukeshay.discord.config.Config
import com.lukeshay.discord.entities.GuildConfigAdminIds
import com.lukeshay.discord.entities.GuildConfigAdminRoleIds
import com.lukeshay.discord.entities.GuildConfigs
import com.lukeshay.discord.entities.Quotes
import com.lukeshay.discord.enums.Environment
import io.akeyless.client.api.V2Api
import io.akeyless.client.model.Configure
import io.akeyless.client.model.GetSecretValue
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import javax.sql.DataSource

fun loadSecrets() {
    val apiClient = io.akeyless.client.Configuration.getDefaultApiClient()

    apiClient.basePath = "https://api.akeyless.io"

    val v2Api = V2Api(apiClient)

    val aKeylessToken = v2Api.configure(
        Configure().accessId(
            System.getProperty("akeyless.access.id")
                ?: throw Exception("akeyless.access.id not found")
        ).accessKey(
            System.getProperty("akeyless.access.key")
                ?: throw Exception("akeyless.access.key not found")
        )
    ).token
        ?: throw Exception("error getting v2 api token")

    val secretsPath = "jeffery-krueger/${Environment.determineEnv().toString().toLowerCase()}"

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

    System.setProperty("discord.token", discordToken)
    System.setProperty("snowflake.url", snowflakeUrl)
    System.setProperty("snowflake.client.secret", snowflakeClientSecret)
}

fun main() {
    loadSecrets()

    val ctx = AnnotationConfigApplicationContext(Config::class.java)
    Database.connect(ctx.getBean(DataSource::class.java))

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(GuildConfigs, GuildConfigAdminIds, GuildConfigAdminRoleIds, Quotes)
    }

    val jda = ctx.getBean(Bot::class.java).start()

    jda.awaitReady()
}
