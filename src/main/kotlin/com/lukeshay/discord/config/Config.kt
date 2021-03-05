package com.lukeshay.discord.config

import com.beust.klaxon.Klaxon
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.jobs.Job
import com.lukeshay.discord.logging.createLogger
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest

@Configuration
@ComponentScan("com.lukeshay.discord")
@EnableJpaRepositories("com.lukeshay.discord.repositories")
@EnableTransactionManagement
class Config {

    @Bean
    fun jdaBuilder(
        listeners: List<ListenerAdapter>,
        jobs: List<Job>,
        secrets: Secrets
    ): JDABuilder {
        val builder = JDABuilder.createDefault(secrets.discordToken)

        builder.setAutoReconnect(true)
        builder.setStatus(OnlineStatus.ONLINE)

        return builder
    }

    @Bean
    fun environment(): Environment {
        return Environment.determineEnv()
    }

    @Bean
    fun httpClient(): HttpClient {
        return HttpClient.newBuilder().build()
    }

    @Bean
    fun klaxon(): Klaxon {
        return Klaxon()
    }

    @Bean
    fun snowflakeHttpRequest(environment: Environment, secrets: Secrets): HttpRequest {
        val builder = HttpRequest.newBuilder().uri(URI.create(secrets.snowflakeUrl))

        try {
            builder.header("X-Client-Secret", secrets.snowflakeClientSecret)
        } catch (e: NullPointerException) {
        }

        return builder.build()
    }
}
