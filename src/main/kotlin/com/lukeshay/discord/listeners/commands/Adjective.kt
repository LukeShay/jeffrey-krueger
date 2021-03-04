package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.services.GuildConfigService
import com.lukeshay.discord.services.WordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Adjective @Autowired constructor(
    private val wordService: WordService,
    private val guildConfigService: GuildConfigService,
    environment: Environment
) :
    WordCommand(
        "adjective",
        "I will add the adjective to the database. Usage: \"singular\" \"plural\"",
        true,
        environment,
        WordType.ADJECTIVE,
        guildConfigService,
        wordService
    )
