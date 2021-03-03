package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.SpringTestBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class VerbTest @Autowired constructor(private val verb: Verb) :
    SpringTestBase() {
    @Test
    fun constructor_correctValues() {
        Assertions.assertEquals("!verb", verb.command)
        Assertions.assertEquals(0, verb.aliases.size)
    }
}
