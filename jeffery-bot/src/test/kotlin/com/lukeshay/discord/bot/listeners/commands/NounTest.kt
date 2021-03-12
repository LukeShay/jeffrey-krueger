package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.bot.enums.Environment
import io.kotest.core.spec.style.ShouldSpec
import org.junit.jupiter.api.Assertions

internal class NounTest : ShouldSpec({
    context("constructor") {
        should("set correct values") {
            val noun = Noun(Environment.determineEnvironment())

            Assertions.assertEquals("!noun", noun.command)
            Assertions.assertEquals(0, noun.aliases.size)
        }
    }
})