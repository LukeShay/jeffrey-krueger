package com.lukeshay.discord.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "words")
data class Word(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
    @CreatedDate @Column(name = "created_date") val createdDate: Instant = Instant.now(),
    @LastModifiedDate @Column(name = "last_modified_date") val lastModifiedDate: Instant = Instant.now(),
    @Enumerated(value = EnumType.STRING) @Column(length = 9) val type: WordType = WordType.VERB,
    val singular: String = "",
    val plural: String = ""
)
