package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.enums.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Noun @Autowired constructor(environment: Environment) :
    WordCommand(
        "noun",
        "I will add the noun to the database. Usage: \"singular\" \"plural\"",
        true,
        environment,
        WordType.NOUN,
    )
