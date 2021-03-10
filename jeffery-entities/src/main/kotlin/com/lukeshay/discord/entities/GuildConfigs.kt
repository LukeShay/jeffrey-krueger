package com.lukeshay.discord.entities

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

object GuildConfigs : Table("guild_configs") {
    val id = long("id")
    val defaultChannelId = long("default_channel_id")
    val dailyGreeting = bool("daily_greeting")
    val dailyQuote = bool("daily_quote")
    val commands = bool("commands")
    val ownerId = long("owner_id")
    override val primaryKey = PrimaryKey(id, name = "pk_guild_configs_id")

    fun selectById(guild: Guild): GuildConfig? =
        GuildConfigs.select { id eq guild.idLong }.singleOrNull()
            ?.let(::fromResultRow)

    fun isAdmin(guild: Guild, member: Member?): Boolean {
        val result = selectById(guild)
        return result != null && member != null && (
            result.ownerId == member.idLong ||
                GuildConfigAdminRoleIds.selectByRoleIdAndGuildId(
                member.idLong,
                guild.idLong
            ) != null ||
                GuildConfigAdminIds.selectByAdminIdAndGuildId(
                member.idLong,
                guild.idLong,
            ) != null
            )
    }

    fun insertOrUpdate(guild: Guild): GuildConfig? {
        val selected = selectById(guild)

        if (selected != null) {
            update({ id eq guild.idLong }) {
                it[dailyGreeting] = true
                it[dailyQuote] = true
                it[commands] = true
                it[ownerId] = guild.ownerIdLong
                it[defaultChannelId] = guild.defaultChannel?.idLong ?: 0
            }
        } else {
            insert {
                it[id] = guild.idLong
                it[dailyGreeting] = true
                it[dailyQuote] = true
                it[commands] = true
                it[ownerId] = guild.ownerIdLong
                it[defaultChannelId] = guild.defaultChannel?.idLong ?: 0
            }
        }

        return selectById(guild)
    }

    fun all(): List<GuildConfig> =
        selectAll().map(::fromResultRow)

    private fun fromResultRow(it: ResultRow): GuildConfig =
        GuildConfig(
            id = it[id],
            defaultChannelId = it[defaultChannelId],
            dailyGreeting = it[dailyGreeting],
            dailyQuote = it[dailyQuote],
            commands = it[commands],
            ownerId = it[ownerId],
        )
}