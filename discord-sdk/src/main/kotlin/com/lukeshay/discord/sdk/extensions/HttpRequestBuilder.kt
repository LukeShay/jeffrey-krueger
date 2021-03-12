package com.lukeshay.discord.sdk.extensions

import com.lukeshay.discord.sdk.requests.OAuth2TokenResponse
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header

fun HttpRequestBuilder.addOAuth2Headers(oAuth2TokenResponse: OAuth2TokenResponse) {
    header(
        "Authorization",
        "${oAuth2TokenResponse.tokenType} ${oAuth2TokenResponse.accessToken}",
    )
    header(
        "Refresh",
        "${oAuth2TokenResponse.tokenType} ${oAuth2TokenResponse.refreshToken}",
    )
}