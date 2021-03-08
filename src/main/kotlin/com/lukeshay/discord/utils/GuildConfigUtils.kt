package com.lukeshay.discord.utils

import com.lukeshay.discord.entities.GuildConfig
import com.lukeshay.discord.entities.GuildConfigAdminIds
import com.lukeshay.discord.entities.GuildConfigAdminRoleIds
import com.lukeshay.discord.entities.GuildConfigs
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

object GuildConfigUtils {
    fun selectById(guild: Guild): GuildConfig? =
        GuildConfigs.select { GuildConfigs.id eq guild.idLong }.singleOrNull()
            ?.let { fromResultRow(it) }

    fun selectRoleByGuildIdAndRoleId(guildId: Long, roleId: Long): ResultRow? =
        GuildConfigAdminRoleIds.select { (GuildConfigAdminRoleIds.guildConfigId eq guildId) and (GuildConfigAdminRoleIds.roleId eq roleId) }
            .firstOrNull()

    fun selectAdminByGuildIdAndAdminId(guildId: Long, adminId: Long): ResultRow? =
        GuildConfigAdminIds.select { (GuildConfigAdminIds.guildConfigId eq guildId) and (GuildConfigAdminIds.adminId eq adminId) }
            .firstOrNull()

    fun isAdmin(guild: Guild, member: Member?): Boolean {
        val result = selectById(guild)
        return result != null && member != null && (
            result.ownerId == member.idLong ||
                selectRoleByGuildIdAndRoleId(
                    guild.idLong,
                    member.idLong
                ) != null ||
                selectAdminByGuildIdAndAdminId(
                    guild.idLong,
                    member.idLong
                ) != null
            )
    }

    fun insertOrUpdate(guild: Guild): GuildConfig? {
        val selected = selectById(guild)

        if (selected != null) {
            GuildConfigs.update({ GuildConfigs.id eq guild.idLong }) {
                it[dailyGreeting] = true
                it[dailyQuote] = true
                it[commands] = true
                it[ownerId] = guild.ownerIdLong
                it[defaultChannelId] = guild.defaultChannel?.idLong ?: 0
            }
        } else {
            GuildConfigs.insert {
                it[id] = guild.idLong
                it[dailyGreeting] = true
                it[dailyQuote] = true
                it[commands] = true
                it[ownerId] = guild.ownerIdLong
                it[defaultChannelId] = guild.defaultChannel?.idLong ?: 0
            }
        }

        return GuildConfigs.select { GuildConfigs.id eq guild.idLong }.singleOrNull()
            ?.let { fromResultRow(it) }
    }

    fun selectAll(): List<GuildConfig> =
        GuildConfigs.selectAll().map { fromResultRow(it) }

    private fun fromResultRow(it: ResultRow): GuildConfig =
        GuildConfig(
            id = it[GuildConfigs.id],
            defaultChannelId = it[GuildConfigs.defaultChannelId],
            dailyGreeting = it[GuildConfigs.dailyGreeting],
            dailyQuote = it[GuildConfigs.dailyQuote],
            commands = it[GuildConfigs.commands],
            ownerId = it[GuildConfigs.ownerId],
        )
}
