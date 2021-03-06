package com.lukeshay.discord.config

import com.beust.klaxon.Klaxon
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.jobs.Job
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest

@Configuration
@ComponentScan("com.lukeshay.discord")
class Config {

    @Bean
    fun jdaBuilder(
        listeners: List<ListenerAdapter>,
        jobs: List<Job>,
    ): JDABuilder {
        val builder = JDABuilder.createDefault(System.getProperty("discord.token"))

        builder.setAutoReconnect(true)
        builder.setStatus(OnlineStatus.ONLINE)

        return builder
    }

    @Bean
    fun environment(): Environment {
        return Environment.determineEnv()
    }
}
