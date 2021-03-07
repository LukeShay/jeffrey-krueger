package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class HeyJeffTest() {
    @Test
    fun `constructor sets correct values`() {
        val heyJeff = HeyJeff(Environment.determineEnvironment())

        Assertions.assertEquals("hey jeff", heyJeff.command)
        Assertions.assertEquals(2, heyJeff.aliases.size)
    }
}
