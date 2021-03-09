package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.enums.Environment

class Adjective(environment: Environment) :
    WordCommand(
        "adjective",
        "I will add the adjective to the database. Usage: \"singular\" \"plural\"",
        true,
        environment,
        WordType.ADJECTIVE,
    )