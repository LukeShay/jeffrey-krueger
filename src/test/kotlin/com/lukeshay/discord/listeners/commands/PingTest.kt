package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.SpringTestBase
import com.lukeshay.discord.enums.FeatureStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PingTest @Autowired constructor(private val ping: Ping) :
    SpringTestBase() {
    @Test
    fun constructor_correctValues() {
        Assertions.assertEquals("!ping", ping.command)
        Assertions.assertEquals(0, ping.aliases.size)
        Assertions.assertEquals(FeatureStatus.RELEASE, ping.status)
    }
}
