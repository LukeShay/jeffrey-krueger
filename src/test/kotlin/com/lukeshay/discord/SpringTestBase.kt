package com.lukeshay.discord

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DisplayName("Adjective Test")
@ContextConfiguration(classes = [SpringConfig::class])
@ActiveProfiles("test")
open class SpringTestBase
