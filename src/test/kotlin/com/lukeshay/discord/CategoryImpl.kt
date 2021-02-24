package com.lukeshay.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.GuildChannel
import net.dv8tion.jda.api.entities.IPermissionHolder
import net.dv8tion.jda.api.entities.Invite
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.PermissionOverride
import net.dv8tion.jda.api.entities.StoreChannel
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.managers.ChannelManager
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction
import net.dv8tion.jda.api.requests.restaction.ChannelAction
import net.dv8tion.jda.api.requests.restaction.InviteAction
import net.dv8tion.jda.api.requests.restaction.PermissionOverrideAction
import net.dv8tion.jda.api.requests.restaction.order.CategoryOrderAction

class CategoryImpl(private val id: Long, private val channels: List<GuildChannel>) : Category {
    override fun getIdLong(): Long {
        return id
    }

    override fun compareTo(other: GuildChannel?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(): ChannelType {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getGuild(): Guild {
        TODO("Not yet implemented")
    }

    override fun getParent(): Category? {
        TODO("Not yet implemented")
    }

    override fun getMembers(): MutableList<Member> {
        TODO("Not yet implemented")
    }

    override fun getPosition(): Int {
        TODO("Not yet implemented")
    }

    override fun getPositionRaw(): Int {
        TODO("Not yet implemented")
    }

    override fun getJDA(): JDA {
        TODO("Not yet implemented")
    }

    override fun getPermissionOverride(permissionHolder: IPermissionHolder): PermissionOverride? {
        TODO("Not yet implemented")
    }

    override fun getPermissionOverrides(): MutableList<PermissionOverride> {
        TODO("Not yet implemented")
    }

    override fun getMemberPermissionOverrides(): MutableList<PermissionOverride> {
        TODO("Not yet implemented")
    }

    override fun getRolePermissionOverrides(): MutableList<PermissionOverride> {
        TODO("Not yet implemented")
    }

    override fun isSynced(): Boolean {
        TODO("Not yet implemented")
    }

    override fun createCopy(guild: Guild): ChannelAction<Category> {
        TODO("Not yet implemented")
    }

    override fun createCopy(): ChannelAction<Category> {
        TODO("Not yet implemented")
    }

    override fun getManager(): ChannelManager {
        TODO("Not yet implemented")
    }

    override fun delete(): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun createPermissionOverride(permissionHolder: IPermissionHolder): PermissionOverrideAction {
        TODO("Not yet implemented")
    }

    override fun putPermissionOverride(permissionHolder: IPermissionHolder): PermissionOverrideAction {
        TODO("Not yet implemented")
    }

    override fun createInvite(): InviteAction {
        TODO("Not yet implemented")
    }

    override fun retrieveInvites(): RestAction<MutableList<Invite>> {
        TODO("Not yet implemented")
    }

    override fun getChannels(): MutableList<GuildChannel> {
        return channels.toMutableList()
    }

    override fun getStoreChannels(): MutableList<StoreChannel> {
        TODO("Not yet implemented")
    }

    override fun getTextChannels(): MutableList<TextChannel> {
        TODO("Not yet implemented")
    }

    override fun getVoiceChannels(): MutableList<VoiceChannel> {
        TODO("Not yet implemented")
    }

    override fun createTextChannel(name: String): ChannelAction<TextChannel> {
        TODO("Not yet implemented")
    }

    override fun createVoiceChannel(name: String): ChannelAction<VoiceChannel> {
        TODO("Not yet implemented")
    }

    override fun modifyTextChannelPositions(): CategoryOrderAction {
        TODO("Not yet implemented")
    }

    override fun modifyVoiceChannelPositions(): CategoryOrderAction {
        TODO("Not yet implemented")
    }
}
