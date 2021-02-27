package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.FeatureStatus
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Feature @Autowired constructor(environment: Environment) :
    Command(
        "feature",
        "I will reply with the feature request link.",
        true,
        FeatureStatus.RELEASE,
        environment
    ) {
    override fun run(event: GuildMessageReceivedEvent) {
        event.message.reply("https://github.com/LukeShay/jeffrey-krueger/issues/new?assignees=LukeShay&labels=feature%2C+awaiting+triage&template=feature_request.md&title=%5BFEATURE%5D+Your+title+here")
            .queue()
    }
}
