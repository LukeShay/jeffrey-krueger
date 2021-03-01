package com.lukeshay.discord.config

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.jobs.Job
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.lukeshay.discrod")
class Config {

    @Bean
    fun jdaBuilder(listeners: List<ListenerAdapter>, jobs: List<Job>): JDABuilder {
        val builder =
            JDABuilder.createDefault(System.getenv("DISCORD_TOKEN").orEmpty())

        builder.setAutoReconnect(true)
        builder.setStatus(OnlineStatus.ONLINE)

        return builder
    }

    @Bean
    fun environment(): Environment {
        return Environment.determineEnv()
    }
}
