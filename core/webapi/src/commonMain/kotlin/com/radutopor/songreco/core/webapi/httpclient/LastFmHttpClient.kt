package com.radutopor.songreco.core.webapi.httpclient

import SongReco.core.webapi.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun getLastFmHttpClient(
    engine: HttpClientEngine = platformHttpEngine(),    // Use platform specific HTTP engine (i.e. OkHttp for Android, Darwin for iOS)
): HttpClient =
    HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Resources)
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "ws.audioscrobbler.com/2.0"
                parameters.append("api_key", BuildConfig.LASTFM_API_KEY)
                parameters.append("format", "json")
            }
        }
    }
