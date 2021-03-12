package com.lukeshay.discord.sdk

import com.lukeshay.discord.sdk.domain.DiscordError

class DiscordRequestException(msg: String, val response: DiscordError) : Throwable(msg)