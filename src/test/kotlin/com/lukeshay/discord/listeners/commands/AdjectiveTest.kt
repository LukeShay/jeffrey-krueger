package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import io.kotest.core.spec.style.ShouldSpec
import org.junit.jupiter.api.Assertions

class AdjectiveTest : ShouldSpec({
    context("constructor") {
        should("set correct values") {
            val adjective = Adjective(Environment.determineEnvironment())

            Assertions.assertEquals("!adjective", adjective.command)
            Assertions.assertEquals(0, adjective.aliases.size)
        }
    }
})
