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
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "guild_configs")
class GuildConfig(
    @Id val id: Long = 0,
    @Column(name = "created_date") @CreatedDate val createdDate: Instant = Instant.now(),
    @Column(name = "last_modified_date") @LastModifiedDate val lastModifiedDate: Instant = Instant.now(),
    @Column(
        name = "default_channel_id",
        columnDefinition = "BIGINT NOT NULL"
    ) var defaultChannelId: Long = 0,
    @Column(
        name = "daily_quote",
        columnDefinition = "BOOLEAN NOT NULL DEFAULT false"
    ) var dailyQuote: Boolean = true,
    @Column(
        name = "daily_greeting",
        columnDefinition = "BOOLEAN NOT NULL DEFAULT true"
    ) var dailyGreeting: Boolean = true,
    @Column(
        name = "commands",
        columnDefinition = "BOOLEAN NOT NULL DEFAULT true"
    ) var commands: Boolean = true,
    @Column(
        name = "owner_id",
        columnDefinition = "BIGINT NOT NULL"
    ) var ownerId: Long = 0,
    @ElementCollection @LazyCollection(LazyCollectionOption.FALSE) var adminRoleIds: MutableSet<Long> = mutableSetOf(),
    @ElementCollection @LazyCollection(LazyCollectionOption.FALSE) var adminIds: MutableSet<Long> = mutableSetOf()
) {
    fun canEdit(member: Member): Boolean {
        for (role in member.roles) {
            if (adminRoleIds.contains(role.idLong)) return true
        }

        return adminIds.contains(member.idLong) || member.idLong == ownerId || member.isOwner
    }
}
