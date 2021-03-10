package com.lukeshay.discord.entities

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

object GuildConfigAdminRoleIds : Table("guild_config_admin_role_ids") {
    val roleId = long("role_id")
    val guildConfigId = long("guild_config_id") references GuildConfigs.id
    override val primaryKey = PrimaryKey(roleId, name = "pk_guild_config_admin_role_ids_id")

    fun selectByRoleIdAndGuildId(roleId: Long, guildId: Long): ResultRow? =
        GuildConfigAdminRoleIds.select { (guildConfigId eq guildId) and (GuildConfigAdminRoleIds.roleId eq roleId) }
            .firstOrNull()
}