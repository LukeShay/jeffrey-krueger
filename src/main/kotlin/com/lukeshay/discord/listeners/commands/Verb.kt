package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.WordType
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.FeatureStatus
import com.lukeshay.discord.logging.DBLogger
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component
import com.lukeshay.discord.services.MemberService
import com.lukeshay.discord.services.WordService

@Component
class Verb @Autowired constructor(
    private val wordService: WordService,
    private val memberService: MemberService,
    environment: Environment
) :
    Command("verb", "adds the verbs to the database", true, FeatureStatus.PRE_ALPHA, environment) {

    override fun run(event: GuildMessageReceivedEvent) {
        val splitMessage = getRawContent(event).split(" ")

        memberService.getMemberRoles(event, event.author.idLong)

        if (splitMessage.size != 3) {
            event.message.reply("That is an invalid number of arguments. Example !verb summoner summoners")
                .queue()
        } else {
            val singular = splitMessage[1]
            val plural = splitMessage[2]

            try {
                wordService.new(singular, plural, WordType.VERB)?.let {
                    event.message.reply("Your verbs has been saved!").queue()
                } ?: event.message.reply("Those verbs already exists").queue()
            } catch (e: DataAccessException) {
                e.printStackTrace()
                logger.severe("error saving verb: $e")
                event.message.reply("There was an error saving your verbs!").queue()
            }
        }
    }

    companion object {
        private val logger = DBLogger("Verb")
    }
}
