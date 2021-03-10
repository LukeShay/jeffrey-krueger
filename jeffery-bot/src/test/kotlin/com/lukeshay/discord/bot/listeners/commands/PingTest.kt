package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.bot.enums.Environment
import io.kotest.core.spec.style.ShouldSpec
import org.junit.jupiter.api.Assertions

internal class PingTest : ShouldSpec({
    context("constructor") {
        should("set correct values") {
            val ping = Ping(Environment.determineEnvironment())

            Assertions.assertEquals("!ping", ping.command)
            Assertions.assertEquals(0, ping.aliases.size)
        }
    }
})