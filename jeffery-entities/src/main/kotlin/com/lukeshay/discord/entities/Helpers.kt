package com.lukeshay.discord.entities

import javax.sql.DataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun setupExposed(dataSource: DataSource) {
    Database.connect(dataSource)

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(
            GuildConfigs,
            GuildConfigAdminIds,
            GuildConfigAdminRoleIds,
            Quotes,
            Words
        )
    }
}