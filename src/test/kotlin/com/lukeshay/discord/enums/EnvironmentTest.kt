package com.lukeshay.discord.enums

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EnvironmentTest {
    @Test
    fun `determineEnv returns local`() {
        val original = System.getProperty("environment", "local")
        System.setProperty("environment", "local")

        Assertions.assertEquals(Environment.LOCAL, Environment.determineEnvironment())

        System.setProperty("environment", original)
    }

    @Test
    fun `determineEnv returns development`() {
        val original = System.getProperty("environment", "development")
        System.setProperty("environment", "development")

        Assertions.assertEquals(Environment.DEVELOPMENT, Environment.determineEnvironment())

        System.setProperty("environment", original)
    }

    @Test
    fun `determineEnv returns staging`() {
        val original = System.getProperty("environment", "staging")
        System.setProperty("environment", "staging")

        Assertions.assertEquals(Environment.STAGING, Environment.determineEnvironment())

        System.setProperty("environment", original)
    }

    @Test
    fun `determineEnv returns production`() {
        val original = System.getProperty("environment", "production")
        System.setProperty("environment", "production")

        Assertions.assertEquals(Environment.PRODUCTION, Environment.determineEnvironment())

        System.setProperty("environment", original)
    }
}
