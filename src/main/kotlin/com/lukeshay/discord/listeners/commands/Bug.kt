package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.FeatureStatus
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Bug @Autowired constructor(environment: Environment) :
    Command(
        "bug",
        "I will reply with the bug report link.",
        true,
        FeatureStatus.RELEASE,
        environment
    ) {
    override fun run(event: GuildMessageReceivedEvent) {
        event.message.reply("https://github.com/LukeShay/jeffrey-krueger/issues/new?assignees=LukeShay&labels=bug%2C+awaiting+triage&template=bug_report.md&title=%5BBUG%5D+Your+title+here")
            .queue()
    }
}