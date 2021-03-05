package com.lukeshay.discord.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "quotes")
class Quote(
    @Id val id: Long = 0,
    @Column(name = "created_date") @CreatedDate val createdDate: Instant = Instant.now(),
    @Column(name = "last_modified_date") @LastModifiedDate val lastModifiedDate: Instant = Instant.now(),
    @Column(
        name = "guild_id",
        columnDefinition = "BIGINT NOT NULL DEFAULT 0"
    ) val guildId: Long = 0,
    @Column(
        name = "author",
        columnDefinition = "VARCHAR(50) NOT NULL"
    ) val author: String = "",
    @Column(name = "quote", columnDefinition = "VARCHAR(255) NOT NULL") val quote: String = "",
    @Column(name = "date", columnDefinition = "VARCHAR(17)") val date: String? = "",
) {
    fun format(): String {
        return "\"$quote\" - $author${if (date != null && date != "") ", $date" else ""}"
    }
}
