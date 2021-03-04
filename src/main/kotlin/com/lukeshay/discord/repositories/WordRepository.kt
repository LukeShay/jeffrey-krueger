package com.lukeshay.discord.repositories

import com.lukeshay.discord.entities.Word
import org.springframework.data.jpa.repository.JpaRepository

interface WordRepository : JpaRepository<Word, Long> {
    fun findBySlug(slug: String): Word?
    fun findAllByGuildIdAndType(guildId: Long, type: String): List<Word>
}
