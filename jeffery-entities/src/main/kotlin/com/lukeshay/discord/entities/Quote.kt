package com.lukeshay.discord.entities

class Quote(
    val id: Long,
    val author: String?,
    val quote: String,
    val date: String?,
    val guildId: Long
) {
    fun format() = "\"${quote}\" - ${author}${if (date != null && date != "") ", $date" else ""}"
}