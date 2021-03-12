package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.bot.enums.Environment
import io.kotest.core.spec.style.ShouldSpec
import org.junit.jupiter.api.Assertions

internal class FeatureTest : ShouldSpec({
    context("constructor") {
        should("set correct values") {
            val feature = Feature(Environment.determineEnvironment())

            Assertions.assertEquals("!feature", feature.command)
            Assertions.assertEquals(0, feature.aliases.size)
        }
    }
})