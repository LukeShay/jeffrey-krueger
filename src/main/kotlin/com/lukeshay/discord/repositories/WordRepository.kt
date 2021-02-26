package com.lukeshay.discord.repositories

import com.lukeshay.discord.domain.Word
import org.springframework.data.jpa.repository.JpaRepository

interface WordRepository : JpaRepository<Word, Long> {
    fun getBySlug(slug: String): Word?
    fun findAllByType(type: String): List<Word>
}
