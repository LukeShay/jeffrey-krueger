package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class VerbTest() {
    @Test
    fun `constructor sets correct values`() {
        val verb = Verb(Environment.determineEnvironment())

        Assertions.assertEquals("!verb", verb.command)
        Assertions.assertEquals(0, verb.aliases.size)
    }
}
