package com.lukeshay.discord.domain

import com.beust.klaxon.Json

data class Snowflake(@Json(name = "id") val id: Long = 0)
