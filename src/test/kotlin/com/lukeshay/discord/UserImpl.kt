package com.lukeshay.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.PrivateChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.requests.RestAction
import java.util.EnumSet

class UserImpl(
    private val name: String,
    private val isBot: Boolean,
    private val userId: String = Math.random().toLong().toString()
) : User {
    override fun getIdLong(): Long {
        return userId.toLongOrNull(10) ?: Math.random().toLong()
    }

    override fun getAsMention(): String {
        TODO("Not yet implemented")
    }

    override fun isFake(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return name
    }

    override fun getDiscriminator(): String {
        TODO("Not yet implemented")
    }

    override fun getAvatarId(): String? {
        TODO("Not yet implemented")
    }

    override fun getDefaultAvatarId(): String {
        TODO("Not yet implemented")
    }

    override fun getAsTag(): String {
        TODO("Not yet implemented")
    }

    override fun hasPrivateChannel(): Boolean {
        TODO("Not yet implemented")
    }

    override fun openPrivateChannel(): RestAction<PrivateChannel> {
        TODO("Not yet implemented")
    }

    override fun getMutualGuilds(): MutableList<Guild> {
        TODO("Not yet implemented")
    }

    override fun isBot(): Boolean {
        return isBot
    }

    override fun getJDA(): JDA {
        TODO("Not yet implemented")
    }

    override fun getFlags(): EnumSet<User.UserFlag> {
        TODO("Not yet implemented")
    }

    override fun getFlagsRaw(): Int {
        TODO("Not yet implemented")
    }
}
