package com.lukeshay.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.Emote
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.IMentionable
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageActivity
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.MessageReaction
import net.dv8tion.jda.api.entities.MessageType
import net.dv8tion.jda.api.entities.PrivateChannel
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction
import net.dv8tion.jda.api.requests.restaction.MessageAction
import net.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction
import org.apache.commons.collections4.Bag
import java.time.OffsetDateTime
import java.util.EnumSet
import java.util.Formatter

class MessageImpl(private val author: User, private val contentRaw: String) : Message {
    override fun getIdLong(): Long {
        TODO("Not yet implemented")
    }

    override fun formatTo(formatter: Formatter?, flags: Int, width: Int, precision: Int) {
        TODO("Not yet implemented")
    }

    override fun getReferencedMessage(): Message? {
        TODO("Not yet implemented")
    }

    override fun getMentionedUsers(): MutableList<User> {
        TODO("Not yet implemented")
    }

    override fun getMentionedUsersBag(): Bag<User> {
        TODO("Not yet implemented")
    }

    override fun getMentionedChannels(): MutableList<TextChannel> {
        TODO("Not yet implemented")
    }

    override fun getMentionedChannelsBag(): Bag<TextChannel> {
        TODO("Not yet implemented")
    }

    override fun getMentionedRoles(): MutableList<Role> {
        TODO("Not yet implemented")
    }

    override fun getMentionedRolesBag(): Bag<Role> {
        TODO("Not yet implemented")
    }

    override fun getMentionedMembers(guild: Guild): MutableList<Member> {
        TODO("Not yet implemented")
    }

    override fun getMentionedMembers(): MutableList<Member> {
        TODO("Not yet implemented")
    }

    override fun getMentions(vararg types: Message.MentionType?): MutableList<IMentionable> {
        TODO("Not yet implemented")
    }

    override fun isMentioned(
        mentionable: IMentionable,
        vararg types: Message.MentionType?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun mentionsEveryone(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEdited(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTimeEdited(): OffsetDateTime? {
        TODO("Not yet implemented")
    }

    override fun getAuthor(): User {
        return author
    }

    override fun getMember(): Member? {
        TODO("Not yet implemented")
    }

    override fun getJumpUrl(): String {
        TODO("Not yet implemented")
    }

    override fun getContentDisplay(): String {
        TODO("Not yet implemented")
    }

    override fun getContentRaw(): String {
        return contentRaw
    }

    override fun getContentStripped(): String {
        TODO("Not yet implemented")
    }

    override fun getInvites(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun getNonce(): String? {
        TODO("Not yet implemented")
    }

    override fun isFromType(type: ChannelType): Boolean {
        TODO("Not yet implemented")
    }

    override fun getChannelType(): ChannelType {
        TODO("Not yet implemented")
    }

    override fun isWebhookMessage(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getChannel(): MessageChannel {
        TODO("Not yet implemented")
    }

    override fun getPrivateChannel(): PrivateChannel {
        TODO("Not yet implemented")
    }

    override fun getTextChannel(): TextChannel {
        TODO("Not yet implemented")
    }

    override fun getCategory(): Category? {
        TODO("Not yet implemented")
    }

    override fun getGuild(): Guild {
        TODO("Not yet implemented")
    }

    override fun getAttachments(): MutableList<Message.Attachment> {
        TODO("Not yet implemented")
    }

    override fun getEmbeds(): MutableList<MessageEmbed> {
        TODO("Not yet implemented")
    }

    override fun getEmotes(): MutableList<Emote> {
        TODO("Not yet implemented")
    }

    override fun getEmotesBag(): Bag<Emote> {
        TODO("Not yet implemented")
    }

    override fun getReactions(): MutableList<MessageReaction> {
        TODO("Not yet implemented")
    }

    override fun isTTS(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getActivity(): MessageActivity? {
        TODO("Not yet implemented")
    }

    override fun editMessage(newContent: CharSequence): MessageAction {
        TODO("Not yet implemented")
    }

    override fun editMessage(newContent: MessageEmbed): MessageAction {
        TODO("Not yet implemented")
    }

    override fun editMessage(newContent: Message): MessageAction {
        TODO("Not yet implemented")
    }

    override fun editMessageFormat(format: String, vararg args: Any?): MessageAction {
        TODO("Not yet implemented")
    }

    override fun delete(): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun getJDA(): JDA {
        TODO("Not yet implemented")
    }

    override fun isPinned(): Boolean {
        TODO("Not yet implemented")
    }

    override fun pin(): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun unpin(): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun addReaction(emote: Emote): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun addReaction(unicode: String): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun clearReactions(): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun clearReactions(unicode: String): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun clearReactions(emote: Emote): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun removeReaction(emote: Emote): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun removeReaction(emote: Emote, user: User): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun removeReaction(unicode: String): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun removeReaction(unicode: String, user: User): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun retrieveReactionUsers(emote: Emote): ReactionPaginationAction {
        TODO("Not yet implemented")
    }

    override fun retrieveReactionUsers(unicode: String): ReactionPaginationAction {
        TODO("Not yet implemented")
    }

    override fun getReactionByUnicode(unicode: String): MessageReaction.ReactionEmote? {
        TODO("Not yet implemented")
    }

    override fun getReactionById(id: String): MessageReaction.ReactionEmote? {
        TODO("Not yet implemented")
    }

    override fun getReactionById(id: Long): MessageReaction.ReactionEmote? {
        TODO("Not yet implemented")
    }

    override fun suppressEmbeds(suppressed: Boolean): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun crosspost(): RestAction<Message> {
        TODO("Not yet implemented")
    }

    override fun isSuppressedEmbeds(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFlags(): EnumSet<Message.MessageFlag> {
        TODO("Not yet implemented")
    }

    override fun getType(): MessageType {
        TODO("Not yet implemented")
    }
}
