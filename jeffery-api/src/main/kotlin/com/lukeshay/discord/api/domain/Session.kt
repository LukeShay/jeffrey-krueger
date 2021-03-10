package com.lukeshay.discord.api.domain

import kotlinx.serialization.Serializable

@Serializable
data class Session(val code: String = "")