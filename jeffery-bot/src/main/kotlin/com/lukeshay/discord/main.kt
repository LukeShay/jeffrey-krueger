package com.lukeshay.discord

import com.lukeshay.discord.entities.GuildConfigAdminIds
import com.lukeshay.discord.entities.GuildConfigAdminRoleIds
import com.lukeshay.discord.entities.GuildConfigs
import com.lukeshay.discord.entities.Quotes
import com.lukeshay.discord.entities.Words
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.jobs.DailyGreeting
import com.lukeshay.discord.jobs.DailyQuote
import com.lukeshay.discord.jobs.Job
import com.lukeshay.discord.listeners.OnConnectionChange
import com.lukeshay.discord.listeners.OnGuildJoin
import com.lukeshay.discord.listeners.OnGuildMemberJoin
import com.lukeshay.discord.listeners.OnGuildMessageReceived
import com.lukeshay.discord.listeners.commands.AddQuote
import com.lukeshay.discord.listeners.commands.Adjective
import com.lukeshay.discord.listeners.commands.Bug
import com.lukeshay.discord.listeners.commands.Feature
import com.lukeshay.discord.listeners.commands.HeyJeff
import com.lukeshay.discord.listeners.commands.Init
import com.lukeshay.discord.listeners.commands.Noun
import com.lukeshay.discord.listeners.commands.Ping
import com.lukeshay.discord.listeners.commands.Quote
import com.lukeshay.discord.listeners.commands.Verb
import com.mchange.v2.c3p0.ComboPooledDataSource
import io.akeyless.client.api.V2Api
import io.akeyless.client.model.Configure
import io.akeyless.client.model.GetSecretValue
import io.sentry.Sentry
import javax.sql.DataSource
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.apache.logging.log4j.LogManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

private val databaseURL =
    System.getProperty("database.url") ?: throw Exception("database.url not found")

private const val DB_URL_REGEX =
    """postgres://(?<username>[^:]+):(?<password>[^@]+)@(?<domain>.*)"""

fun dataSource(): DataSource {
    LogManager.getLogger("dataSource").info("database url: $databaseURL")
    val groups = DB_URL_REGEX.toRegex().matchEntire(databaseURL)!!.groups

    val dataSource = ComboPooledDataSource()
    dataSource.jdbcUrl = "jdbc:postgresql://${groups["domain"]!!.value}"
    dataSource.user = groups["username"]!!.value
    dataSource.password = groups["password"]!!.value
    dataSource.driverClass = "org.postgresql.Driver"

    return dataSource
}

fun loadSecrets(environment: Environment) {
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

    val secretsPath =
        "jeffery-krueger/${environment.toString().toLowerCase()}"

    val body = GetSecretValue().addNamesItem("$secretsPath/discord-token")
        .addNamesItem("$secretsPath/snowflake-url")
        .addNamesItem("$secretsPath/snowflake-client-secret")
        .addNamesItem("$secretsPath/sentry-dsn")
        .token(aKeylessToken)

    val result = v2Api.getSecretValue(body)

    val discordToken = result["$secretsPath/discord-token"]
        ?: throw Exception("discord token secret not found")
    val snowflakeUrl = result["$secretsPath/snowflake-url"]
        ?: throw Exception("discord token secret not found")
    val snowflakeClientSecret = result["$secretsPath/snowflake-client-secret"]
        ?: throw Exception("discord token secret not found")
    val sentryDsn = result["$secretsPath/sentry-dsn"]
        ?: throw Exception("sentry dsn secret not found")

    System.setProperty("discord.token", discordToken)
    System.setProperty("snowflake.url", snowflakeUrl)
    System.setProperty("snowflake.client.secret", snowflakeClientSecret)
    System.setProperty("sentry.dsn", sentryDsn)

    Sentry.init { options ->
        options.dsn = sentryDsn
        options.environment = environment.toString().toLowerCase()
    }
}

fun jdaBuilder(): JDABuilder {
    val builder = JDABuilder.createDefault(System.getProperty("discord.token"))

    builder.setAutoReconnect(true)
    builder.setStatus(OnlineStatus.ONLINE)

    return builder
}

fun start(
    listeners: List<ListenerAdapter>,
    jobs: List<Job>,
    builder: JDABuilder = jdaBuilder()
): JDA {
    for (listener in listeners) {
        builder.addEventListeners(listener)
    }

    val jda = builder.build()

    jobs.forEach { job ->
        run {
            job.jda = jda
            job.run()
        }
    }

    return jda
}

fun setupExposed() {
    Database.connect(dataSource())

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(
            GuildConfigs,
            GuildConfigAdminIds,
            GuildConfigAdminRoleIds,
            Quotes,
            Words
        )
    }
}

fun main() {
    val environment = Environment.determineEnvironment()
    loadSecrets(environment)

    setupExposed()

    start(
        listeners = listOf(
            OnConnectionChange(),
            OnGuildJoin(environment),
            OnGuildMemberJoin(environment),
            OnGuildMessageReceived(
                cmds = mutableListOf(
                    Adjective(environment),
                    Bug(environment),
                    Feature(environment),
                    HeyJeff(environment),
                    Init(environment),
                    Noun(environment),
                    Ping(environment),
                    Quote(environment),
                    Verb(environment),
                    AddQuote(environment),
                ),
                environment = environment,
            ),
        ),
        jobs = listOf(
            DailyGreeting(),
            DailyQuote(),
        ),
        builder = jdaBuilder(),
    ).awaitReady()
}