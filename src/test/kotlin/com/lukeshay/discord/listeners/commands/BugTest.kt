package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BugTest() {
    @Test
    fun `constructor sets correct values`() {
        val bug = Bug(Environment.determineEnvironment())

        Assertions.assertEquals("!bug", bug.command)
        Assertions.assertEquals(0, bug.aliases.size)
    }
}
