package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.enums.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Adjective @Autowired constructor(environment: Environment) :
    WordCommand(
        "adjective",
        "I will add the adjective to the database. Usage: \"singular\" \"plural\"",
        true,
        environment,
        WordType.ADJECTIVE,
    )
