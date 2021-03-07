package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class NounTest() {
    @Test
    fun `constructor sets correct values`() {
        val noun = Noun(Environment.determineEnvironment())

        Assertions.assertEquals("!noun", noun.command)
        Assertions.assertEquals(0, noun.aliases.size)
    }
}
