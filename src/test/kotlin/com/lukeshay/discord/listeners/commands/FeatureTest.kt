package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.SpringTestBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class FeatureTest @Autowired constructor(private val feature: Feature) :
    SpringTestBase() {
    @Test
    fun constructor_correctValues() {
        Assertions.assertEquals("!feature", feature.command)
        Assertions.assertEquals(0, feature.aliases.size)
    }
}
