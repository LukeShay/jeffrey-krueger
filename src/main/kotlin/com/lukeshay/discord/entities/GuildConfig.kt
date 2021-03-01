package com.lukeshay.discord.entities

import net.dv8tion.jda.api.entities.Member
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
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
class GuildConfig(
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
        columnDefinition = "VARCHAR(18) NOT NULL default 'REPLACE'"
    ) val ownerId: String = "",
    @ElementCollection @LazyCollection(LazyCollectionOption.FALSE) val adminRoles: Set<String> = setOf(),
    @ElementCollection @LazyCollection(LazyCollectionOption.FALSE) val adminIds: Set<String> = setOf()
) {
    fun canEdit(member: Member): Boolean {
        for (role in member.roles) {
            if (adminRoles.contains(role.name)) return true
        }

        return adminIds.contains(member.id) || member.id == ownerId || member.isOwner
    }
}
