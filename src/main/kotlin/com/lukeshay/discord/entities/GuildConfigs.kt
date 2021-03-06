package com.lukeshay.discord.entities

import org.jetbrains.exposed.sql.Table

object GuildConfigs : Table("guild_configs") {
    val id = long("id")
    val defaultChannelId = long("default_channel_id")
    val dailyGreeting = bool("daily_greeting")
    val dailyQuote = bool("daily_quote")
    val commands = bool("commands")
    val ownerId = long("owner_id")
    override val primaryKey = PrimaryKey(id, name = "pk_guild_configs_id")
}

object GuildConfigAdminIds : Table("guild_config_admin_ids") {
    val adminId = long("admin_id")
    val guildConfigId = long("guild_config_id") references GuildConfigs.id
    override val primaryKey = PrimaryKey(adminId, name = "pk_guild_config_admin_ids_id")
}

object GuildConfigAdminRoleIds : Table("guild_config_admin_role_ids") {
    val roleId = long("role_id")
    val guildConfigId = long("guild_config_id") references GuildConfigs.id
    override val primaryKey = PrimaryKey(roleId, name = "pk_guild_config_admin_role_ids_id")
}
