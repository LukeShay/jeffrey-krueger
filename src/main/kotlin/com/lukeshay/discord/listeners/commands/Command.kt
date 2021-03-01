package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.FeatureStatus
import com.lukeshay.discord.utils.leaderChar
import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

abstract class Command(
    cmd: String,
    desc: String,
    leader: Boolean,
    val status: FeatureStatus,
    val environment: Environment,
    als: List<String> = listOf()
) {
    val command = "${if (leader) leaderChar else ""}${cmd.toLowerCase()}"
    val aliases = als.map { alias -> "${if (leader) leaderChar else ""}${alias.toLowerCase()}" }
    val description = "$desc${listToAliasesStr(aliases)}"

    abstract fun run(event: CommandEvent)

    fun matches(s: String): Boolean {
        return s.startsWith(command, ignoreCase = true) || aliases.find { alias ->
            s.startsWith(
                alias,
                ignoreCase = true
            )
        } != null
    }

    fun isAllowed(category: Category?): Boolean {
        return status.isAllowed(category)
    }

    fun getRawContent(event: GuildMessageReceivedEvent): String {
        return event.message.contentRaw.removePrefix("${environment.toString().toLowerCase()} ")
    }

    override fun toString(): String {
        return "$command: $description"
    }

    companion object {
        private fun listToAliasesStr(l: List<String>): String {
            var str = ""

            l.forEach { i -> str += "$i, " }

            return if (str.length < 2) "" else " - aliases: ${str.substring(0, str.length - 2)}"
        }
    }
}
