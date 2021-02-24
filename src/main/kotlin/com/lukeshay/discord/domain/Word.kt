package com.lukeshay.discord.domain

import com.lukeshay.discord.utils.specialCharacters
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
class Word(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
    @CreatedDate val createdDate: Instant = Instant.now(),
    @LastModifiedDate val lastModifiedDate: Instant = Instant.now(),
    @Enumerated(value = EnumType.STRING) @Column(columnDefinition = "CHAR(9) NOT NULL") val type: WordType = WordType.VERB,
    @Column(columnDefinition = "CHAR(30) NOT NULL") val singular: String = "",
    @Column(columnDefinition = "CHAR(32)") val plural: String = ""
) {
    @Column(columnDefinition = "CHAR(71) NOT NULL UNIQUE")
    val slug = "${specialCharacters.replace(singular.toLowerCase(), "")}-${
    specialCharacters.replace(
        plural.toLowerCase(),
        ""
    )
    }-$type"
}
