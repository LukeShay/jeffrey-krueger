package com.lukeshay.discord.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "guild_configs")
data class GuildConfig(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
    @Column(name = "created_date") @CreatedDate val createdDate: Instant = Instant.now(),
    @Column(name = "last_modified_date") @LastModifiedDate val lastModifiedDate: Instant = Instant.now(),
    @Column(
        name = "guild_id",
        columnDefinition = "VARCHAR(18) NOT NULL UNIQUE"
    ) val guildId: String = "",
    @Column(
        name = "default_channel_id",
        columnDefinition = "VARCHAR(18) NOT NULL"
    ) val defaultChannelId: String = "",
    @Column(
        name = "daily_quote",
        columnDefinition = "BOOLEAN NOT NULL DEFAULT false"
    ) val dailyQuote: Boolean = false,
    @Column(
        name = "daily_greeting",
        columnDefinition = "BOOLEAN NOT NULL DEFAULT false"
    ) val dailyGreeting: Boolean = false,
    @Column(
        name = "owner_id",
        columnDefinition = "VARCHAR(18) NOT NULL default \"REPLACE\""
    ) val ownerId: String = "",
    @ElementCollection val adminRoles: List<String> = listOf(),
    @ElementCollection val adminIds: List<String> = listOf()
)
