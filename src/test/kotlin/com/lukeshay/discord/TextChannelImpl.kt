package com.lukeshay.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.Emote
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.GuildChannel
import net.dv8tion.jda.api.entities.IPermissionHolder
import net.dv8tion.jda.api.entities.Invite
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.PermissionOverride
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.Webhook
import net.dv8tion.jda.api.managers.ChannelManager
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction
import net.dv8tion.jda.api.requests.restaction.ChannelAction
import net.dv8tion.jda.api.requests.restaction.InviteAction
import net.dv8tion.jda.api.requests.restaction.MessageAction
import net.dv8tion.jda.api.requests.restaction.PermissionOverrideAction
import net.dv8tion.jda.api.requests.restaction.WebhookAction

class TextChannelImpl(private val messageAction: MessageAction) : TextChannel {
    override fun sendMessage(text: CharSequence): MessageAction {
        return messageAction
    }

    override fun sendMessage(embed: MessageEmbed): MessageAction {
        return messageAction
    }

    override fun sendMessage(msg: Message): MessageAction {
        return messageAction
    }

    override fun sendMessageFormat(format: String, vararg args: Any?): MessageAction {
        return messageAction
    }

    override fun getIdLong(): Long {
        TODO("Not yet implemented")
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

    override fun createCopy(guild: Guild): ChannelAction<TextChannel> {
        TODO("Not yet implemented")
    }

    override fun createCopy(): ChannelAction<TextChannel> {
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

    override fun getLatestMessageIdLong(): Long {
        TODO("Not yet implemented")
    }

    override fun hasLatestMessage(): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeReactionById(
        messageId: String,
        unicode: String,
        user: User
    ): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun getAsMention(): String {
        TODO("Not yet implemented")
    }

    override fun getTopic(): String? {
        TODO("Not yet implemented")
    }

    override fun isNSFW(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isNews(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getSlowmode(): Int {
        TODO("Not yet implemented")
    }

    override fun retrieveWebhooks(): RestAction<MutableList<Webhook>> {
        TODO("Not yet implemented")
    }

    override fun createWebhook(name: String): WebhookAction {
        TODO("Not yet implemented")
    }

    override fun deleteMessages(messages: MutableCollection<Message>): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteMessagesByIds(messageIds: MutableCollection<String>): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteWebhookById(id: String): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun clearReactionsById(messageId: String): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun clearReactionsById(messageId: String, unicode: String): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun clearReactionsById(messageId: String, emote: Emote): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun canTalk(): Boolean {
        TODO("Not yet implemented")
    }

    override fun canTalk(member: Member): Boolean {
        TODO("Not yet implemented")
    }
}
