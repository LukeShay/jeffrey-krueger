package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.SpringTestBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class AdjectiveTest @Autowired constructor(private val adjective: Adjective) :
    SpringTestBase() {
    @Test
    fun `constructor sets correct values`() {
        Assertions.assertEquals("!adjective", adjective.command)
        Assertions.assertEquals(0, adjective.aliases.size)
    }
}
