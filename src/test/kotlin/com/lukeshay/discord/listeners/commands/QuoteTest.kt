package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.SpringTestBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class QuoteTest @Autowired constructor(private val quote: Quote) :
    SpringTestBase() {
    @Test
    fun `constructor sets correct values`() {
        Assertions.assertEquals("!quote", quote.command)
        Assertions.assertEquals(0, quote.aliases.size)
    }
}
