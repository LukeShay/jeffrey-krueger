package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FeatureTest() {
    @Test
    fun `constructor sets correct values`() {
        val feature = Feature(Environment.determineEnvironment())

        Assertions.assertEquals("!feature", feature.command)
        Assertions.assertEquals(0, feature.aliases.size)
    }
}
