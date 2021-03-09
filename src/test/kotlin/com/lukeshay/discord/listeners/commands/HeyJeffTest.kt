package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import io.kotest.core.spec.style.ShouldSpec
import org.junit.jupiter.api.Assertions

internal class HeyJeffTest : ShouldSpec({
    context("constructor") {
        should("set correct values") {
            val heyJeff = HeyJeff(Environment.determineEnvironment())

            Assertions.assertEquals("hey jeff", heyJeff.command)
            Assertions.assertEquals(2, heyJeff.aliases.size)
        }
    }
})
