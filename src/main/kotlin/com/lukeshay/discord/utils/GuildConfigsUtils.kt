package com.lukeshay.discord.utils

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

fun selectGuildConfigById(guild: Guild) =
    GuildConfigs.select { GuildConfigs.id eq guild.idLong }.singleOrNull()

fun selectGuildConfigRoleByGuildIdAndRoleId(guildId: Long, roleId: Long) =
    GuildConfigAdminRoleIds.select { (GuildConfigAdminRoleIds.guildConfigId eq guildId) and (GuildConfigAdminRoleIds.roleId eq roleId) }
        .firstOrNull()

fun selectGuildConfigAdminByGuildIdAndAdminId(guildId: Long, adminId: Long) =
    GuildConfigAdminIds.select { (GuildConfigAdminIds.guildConfigId eq guildId) and (GuildConfigAdminIds.adminId eq adminId) }
        .firstOrNull()

fun isAdmin(guild: Guild, member: Member?): Boolean {
    val result = selectGuildConfigById(guild)
    return result != null && member != null && (
        result[GuildConfigs.ownerId] == member.idLong ||
            selectGuildConfigRoleByGuildIdAndRoleId(guild.idLong, member.idLong) != null ||
            selectGuildConfigAdminByGuildIdAndAdminId(guild.idLong, member.idLong) != null
        )
}

fun insertOrUpdateGuildConfig(guild: Guild): ResultRow? {
    val selected = selectGuildConfigById(guild)

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
}

fun selectAllGuildConfigs() = GuildConfigs.selectAll().map { it }
