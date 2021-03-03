package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.SpringTestBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class HeyJeffTest @Autowired constructor(private val heyJeff: HeyJeff) :
    SpringTestBase() {
    @Test
    fun `constructor sets correct values`() {
        Assertions.assertEquals("hey jeff", heyJeff.command)
        Assertions.assertEquals(2, heyJeff.aliases.size)
    }
}
