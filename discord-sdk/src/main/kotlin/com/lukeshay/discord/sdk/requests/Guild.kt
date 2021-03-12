package com.lukeshay.discord.sdk.requests

import com.lukeshay.discord.sdk.DiscordRequestException
import com.lukeshay.discord.sdk.DiscordSdk
import com.lukeshay.discord.sdk.domain.DiscordError
import com.lukeshay.discord.sdk.extensions.addOAuth2Headers
import com.lukeshay.discord.sdk.extensions.decodeFromJsonElementIgnoreUnknown
import com.lukeshay.discord.sdk.extensions.json
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger("com.lukeshay.discord.sdk.requests.GuildKt")

@Serializable data class Guild(
    val id: String = "",
    val name: String = "",
    val icon: String = "",
    val description: String? = "",
    val splash: String? = "",
    @SerialName("discovery_splash") val discoverySplash: String? = "",
    @SerialName("approximate_member_count") val approximateMemberCount: Int = 0,
    @SerialName("approximate_presence_count") val approximatePresenceCount: Int = 0,
    val features: List<String> = listOf(),
    val banner: String? = "",
    @SerialName("owner_id") val ownerId: String? = "",
)

suspend fun DiscordSdk.getMyGuilds(oAuth2TokenResponse: OAuth2TokenResponse): List<Guild> {
    try {
        val response: HttpResponse =
            httpClient.get("$discordApiUrl/users/@me/guilds?with_counts=true") {
                addOAuth2Headers(oAuth2TokenResponse)
            }
        return Json.decodeFromJsonElementIgnoreUnknown(response.json())
    } catch (e: ClientRequestException) {
        logger.debug("error when getting guild ${e.message}")
        val discordError = Json.decodeFromJsonElementIgnoreUnknown<DiscordError>(e.response.json())
        throw DiscordRequestException("error getting guild", discordError)
    }
}

suspend fun DiscordSdk.getMyGuildsOrEmpty(oAuth2TokenResponse: OAuth2TokenResponse): List<Guild> {
    try {
        val response: HttpResponse =
            httpClient.get("$discordApiUrl/users/@me/guilds?with_counts=true") {
                addOAuth2Headers(oAuth2TokenResponse)
            }
        return Json.decodeFromJsonElementIgnoreUnknown(response.json())
    } catch (e: Exception) {
        logger.error("error when getting guild ${e.message}")
    }

    return emptyList()
}
