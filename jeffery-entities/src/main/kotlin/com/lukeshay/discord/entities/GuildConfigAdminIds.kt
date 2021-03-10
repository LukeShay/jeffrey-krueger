package com.lukeshay.discord.entities

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

object GuildConfigAdminIds : Table("guild_config_admin_ids") {
    val adminId = long("admin_id")
    val guildConfigId = long("guild_config_id") references GuildConfigs.id
    override val primaryKey = PrimaryKey(adminId, name = "pk_guild_config_admin_ids_id")

    fun selectByAdminIdAndGuildId(adminId: Long, guildId: Long): ResultRow? =
        GuildConfigAdminIds.select { (guildConfigId eq guildId) and (GuildConfigAdminIds.adminId eq adminId) }
            .firstOrNull()
}