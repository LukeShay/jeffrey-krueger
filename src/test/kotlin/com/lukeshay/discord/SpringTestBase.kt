package com.lukeshay.discord

import com.lukeshay.discord.config.Config
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [Config::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class SpringTestBase
