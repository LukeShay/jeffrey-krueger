package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.bot.enums.Environment

class Verb(environment: Environment) :
    WordCommand(
        "verb",
        "I will add the verb to the database. Usage: \"singular\" \"plural\"",
        true,
        environment,
        WordType.VERB,
    )