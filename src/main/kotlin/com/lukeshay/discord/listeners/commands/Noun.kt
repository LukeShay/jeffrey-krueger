package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.enums.Environment

class Noun(environment: Environment) :
    WordCommand(
        "noun",
        "I will add the noun to the database. Usage: \"singular\" \"plural\"",
        true,
        environment,
        WordType.NOUN,
    )
