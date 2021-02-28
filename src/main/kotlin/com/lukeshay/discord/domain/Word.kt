package com.lukeshay.discord.domain

import com.lukeshay.discord.utils.specialCharacters
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "words")
class Word(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
    @Column(name = "created_date") @CreatedDate val createdDate: Instant = Instant.now(),
    @Column(name = "last_modified_date") @LastModifiedDate val lastModifiedDate: Instant = Instant.now(),
    @Column(name = "type", columnDefinition = "VARCHAR(9) NOT NULL") val type: String = "NOUN",
    @Column(name = "singular", columnDefinition = "VARCHAR(30) NOT NULL") val singular: String = "",
    @Column(name = "plural", columnDefinition = "VARCHAR(32)") val plural: String = ""
) {
    @Column(name = "slug", columnDefinition = "VARCHAR(71) NOT NULL UNIQUE")
    val slug = "${specialCharacters.replace(singular.toLowerCase(), "")}-${
    specialCharacters.replace(
        plural.toLowerCase(),
        ""
    )
    }-$type"
}
