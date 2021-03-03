package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.SpringTestBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class BugTest @Autowired constructor(private val bug: Bug) :
    SpringTestBase() {
    @Test
    fun constructor_correctValues() {
        Assertions.assertEquals("!bug", bug.command)
        Assertions.assertEquals(0, bug.aliases.size)
    }
}
