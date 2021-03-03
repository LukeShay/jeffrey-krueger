package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.SpringTestBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class NounTest @Autowired constructor(private val noun: Noun) :
    SpringTestBase() {
    @Test
    fun constructor_correctValues() {
        Assertions.assertEquals("!noun", noun.command)
        Assertions.assertEquals(0, noun.aliases.size)
    }
}
