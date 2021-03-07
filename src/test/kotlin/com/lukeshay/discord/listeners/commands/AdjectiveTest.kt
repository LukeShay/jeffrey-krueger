package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class AdjectiveTest() {
    @Test
    fun `constructor sets correct values`() {
        val adjective = Adjective(Environment.determineEnvironment())

        Assertions.assertEquals("!adjective", adjective.command)
        Assertions.assertEquals(0, adjective.aliases.size)
    }
}
