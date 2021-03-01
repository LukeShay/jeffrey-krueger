package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.FeatureStatus
import com.lukeshay.discord.services.GuildConfigService
import com.lukeshay.discord.services.WordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Noun @Autowired constructor(
    private val wordService: WordService,
    private val guildConfigService: GuildConfigService,
    environment: Environment
) :
    WordCommand(
        "noun",
        "adds the noun to the database",
        true,
        FeatureStatus.PRE_ALPHA,
        environment,
        WordType.NOUN,
        guildConfigService,
        wordService
    )
