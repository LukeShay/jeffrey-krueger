package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class QuoteTest() {
    @Test
    fun `constructor sets correct values`() {
        val quote = Quote(Environment.determineEnvironment())

        Assertions.assertEquals("!quote", quote.command)
        Assertions.assertEquals(0, quote.aliases.size)
    }
}
