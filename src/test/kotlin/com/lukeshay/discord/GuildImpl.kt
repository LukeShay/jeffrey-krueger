package com.lukeshay.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.Region
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.managers.AudioManager
import net.dv8tion.jda.api.managers.GuildManager
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction
import net.dv8tion.jda.api.requests.restaction.ChannelAction
import net.dv8tion.jda.api.requests.restaction.MemberAction
import net.dv8tion.jda.api.requests.restaction.RoleAction
import net.dv8tion.jda.api.requests.restaction.order.CategoryOrderAction
import net.dv8tion.jda.api.requests.restaction.order.ChannelOrderAction
import net.dv8tion.jda.api.requests.restaction.order.RoleOrderAction
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction
import net.dv8tion.jda.api.utils.cache.MemberCacheView
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView
import net.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView
import net.dv8tion.jda.api.utils.concurrent.Task
import java.util.EnumSet
import java.util.Locale
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class GuildImpl(private val name: String) : Guild {
    override fun getIdLong(): Long {
        TODO("Not yet implemented")
    }

    override fun retrieveRegions(includeDeprecated: Boolean): RestAction<EnumSet<Region>> {
        TODO("Not yet implemented")
    }

    override fun addMember(accessToken: String, userId: String): MemberAction {
        TODO("Not yet implemented")
    }

    override fun isLoaded(): Boolean {
        TODO("Not yet implemented")
    }

    override fun pruneMemberCache() {
        TODO("Not yet implemented")
    }

    override fun unloadMember(userId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMemberCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return name
    }

    override fun getIconId(): String? {
        TODO("Not yet implemented")
    }

    override fun getFeatures(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getSplashId(): String? {
        TODO("Not yet implemented")
    }

    override fun retrieveVanityUrl(): RestAction<String> {
        TODO("Not yet implemented")
    }

    override fun getVanityCode(): String? {
        TODO("Not yet implemented")
    }

    override fun getDescription(): String? {
        TODO("Not yet implemented")
    }

    override fun getLocale(): Locale {
        TODO("Not yet implemented")
    }

    override fun getBannerId(): String? {
        TODO("Not yet implemented")
    }

    override fun getBoostTier(): Guild.BoostTier {
        TODO("Not yet implemented")
    }

    override fun getBoostCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getBoosters(): MutableList<Member> {
        TODO("Not yet implemented")
    }

    override fun getMaxMembers(): Int {
        TODO("Not yet implemented")
    }

    override fun getMaxPresences(): Int {
        TODO("Not yet implemented")
    }

    override fun retrieveMetaData(): RestAction<Guild.MetaData> {
        TODO("Not yet implemented")
    }

    override fun getAfkChannel(): VoiceChannel? {
        TODO("Not yet implemented")
    }

    override fun getSystemChannel(): TextChannel? {
        TODO("Not yet implemented")
    }

    override fun getOwner(): Member? {
        TODO("Not yet implemented")
    }

    override fun getOwnerIdLong(): Long {
        TODO("Not yet implemented")
    }

    override fun getAfkTimeout(): Guild.Timeout {
        TODO("Not yet implemented")
    }

    override fun getRegionRaw(): String {
        TODO("Not yet implemented")
    }

    override fun isMember(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun getSelfMember(): Member {
        TODO("Not yet implemented")
    }

    override fun getMember(user: User): Member? {
        TODO("Not yet implemented")
    }

    override fun getMemberCache(): MemberCacheView {
        TODO("Not yet implemented")
    }

    override fun getCategoryCache(): SortedSnowflakeCacheView<Category> {
        TODO("Not yet implemented")
    }

    override fun getStoreChannelCache(): SortedSnowflakeCacheView<StoreChannel> {
        TODO("Not yet implemented")
    }

    override fun getTextChannelCache(): SortedSnowflakeCacheView<TextChannel> {
        TODO("Not yet implemented")
    }

    override fun getVoiceChannelCache(): SortedSnowflakeCacheView<VoiceChannel> {
        TODO("Not yet implemented")
    }

    override fun getChannels(includeHidden: Boolean): MutableList<GuildChannel> {
        TODO("Not yet implemented")
    }

    override fun getRoleCache(): SortedSnowflakeCacheView<Role> {
        TODO("Not yet implemented")
    }

    override fun getEmoteCache(): SnowflakeCacheView<Emote> {
        TODO("Not yet implemented")
    }

    override fun retrieveEmotes(): RestAction<MutableList<ListedEmote>> {
        TODO("Not yet implemented")
    }

    override fun retrieveEmoteById(id: String): RestAction<ListedEmote> {
        TODO("Not yet implemented")
    }

    override fun retrieveBanList(): RestAction<MutableList<Guild.Ban>> {
        TODO("Not yet implemented")
    }

    override fun retrieveBanById(userId: String): RestAction<Guild.Ban> {
        TODO("Not yet implemented")
    }

    override fun retrievePrunableMemberCount(days: Int): RestAction<Int> {
        TODO("Not yet implemented")
    }

    override fun getPublicRole(): Role {
        TODO("Not yet implemented")
    }

    override fun getDefaultChannel(): TextChannel? {
        TODO("Not yet implemented")
    }

    override fun getManager(): GuildManager {
        TODO("Not yet implemented")
    }

    override fun retrieveAuditLogs(): AuditLogPaginationAction {
        TODO("Not yet implemented")
    }

    override fun leave(): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun delete(): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun delete(mfaCode: String?): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun getAudioManager(): AudioManager {
        TODO("Not yet implemented")
    }

    override fun getJDA(): JDA {
        TODO("Not yet implemented")
    }

    override fun retrieveInvites(): RestAction<MutableList<Invite>> {
        TODO("Not yet implemented")
    }

    override fun retrieveWebhooks(): RestAction<MutableList<Webhook>> {
        TODO("Not yet implemented")
    }

    override fun getVoiceStates(): MutableList<GuildVoiceState> {
        TODO("Not yet implemented")
    }

    override fun getVerificationLevel(): Guild.VerificationLevel {
        TODO("Not yet implemented")
    }

    override fun getDefaultNotificationLevel(): Guild.NotificationLevel {
        TODO("Not yet implemented")
    }

    override fun getRequiredMFALevel(): Guild.MFALevel {
        TODO("Not yet implemented")
    }

    override fun getExplicitContentLevel(): Guild.ExplicitContentLevel {
        TODO("Not yet implemented")
    }

    override fun checkVerification(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAvailable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun retrieveMembers(): CompletableFuture<Void> {
        TODO("Not yet implemented")
    }

    override fun loadMembers(callback: Consumer<Member>): Task<Void> {
        TODO("Not yet implemented")
    }

    override fun retrieveMemberById(id: Long, update: Boolean): RestAction<Member> {
        TODO("Not yet implemented")
    }

    override fun retrieveMembersByIds(
        includePresence: Boolean,
        vararg ids: Long
    ): Task<MutableList<Member>> {
        TODO("Not yet implemented")
    }

    override fun retrieveMembersByPrefix(prefix: String, limit: Int): Task<MutableList<Member>> {
        TODO("Not yet implemented")
    }

    override fun moveVoiceMember(member: Member, voiceChannel: VoiceChannel?): RestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun modifyNickname(member: Member, nickname: String?): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun prune(days: Int, wait: Boolean, vararg roles: Role?): AuditableRestAction<Int> {
        TODO("Not yet implemented")
    }

    override fun kick(member: Member, reason: String?): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun kick(userId: String, reason: String?): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun ban(user: User, delDays: Int, reason: String?): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun ban(userId: String, delDays: Int, reason: String?): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun unban(userId: String): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun deafen(member: Member, deafen: Boolean): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun mute(member: Member, mute: Boolean): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun addRoleToMember(member: Member, role: Role): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun removeRoleFromMember(member: Member, role: Role): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun modifyMemberRoles(
        member: Member,
        rolesToAdd: MutableCollection<Role>?,
        rolesToRemove: MutableCollection<Role>?
    ): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun modifyMemberRoles(
        member: Member,
        roles: MutableCollection<Role>
    ): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun transferOwnership(newOwner: Member): AuditableRestAction<Void> {
        TODO("Not yet implemented")
    }

    override fun createTextChannel(name: String, parent: Category?): ChannelAction<TextChannel> {
        TODO("Not yet implemented")
    }

    override fun createVoiceChannel(name: String, parent: Category?): ChannelAction<VoiceChannel> {
        TODO("Not yet implemented")
    }

    override fun createCategory(name: String): ChannelAction<Category> {
        TODO("Not yet implemented")
    }

    override fun createRole(): RoleAction {
        TODO("Not yet implemented")
    }

    override fun createEmote(
        name: String,
        icon: Icon,
        vararg roles: Role?
    ): AuditableRestAction<Emote> {
        TODO("Not yet implemented")
    }

    override fun modifyCategoryPositions(): ChannelOrderAction {
        TODO("Not yet implemented")
    }

    override fun modifyTextChannelPositions(): ChannelOrderAction {
        TODO("Not yet implemented")
    }

    override fun modifyTextChannelPositions(category: Category): CategoryOrderAction {
        TODO("Not yet implemented")
    }

    override fun modifyVoiceChannelPositions(): ChannelOrderAction {
        TODO("Not yet implemented")
    }

    override fun modifyVoiceChannelPositions(category: Category): CategoryOrderAction {
        TODO("Not yet implemented")
    }

    override fun modifyRolePositions(useAscendingOrder: Boolean): RoleOrderAction {
        TODO("Not yet implemented")
    }
}
