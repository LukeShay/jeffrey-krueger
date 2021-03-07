package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PingTest() {
    @Test
    fun `constructor sets correct values`() {
        val ping = Ping(Environment.determineEnvironment())

        Assertions.assertEquals("!ping", ping.command)
        Assertions.assertEquals(0, ping.aliases.size)
    }
}
