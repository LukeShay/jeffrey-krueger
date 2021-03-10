package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.bot.enums.Environment
import io.kotest.core.spec.style.ShouldSpec
import org.junit.jupiter.api.Assertions

internal class QuoteTest : ShouldSpec({
    context("constructor") {
        should("set correct values") {
            val quote = Quote(Environment.determineEnvironment())

            Assertions.assertEquals("!quote", quote.command)
            Assertions.assertEquals(0, quote.aliases.size)
        }
    }
})