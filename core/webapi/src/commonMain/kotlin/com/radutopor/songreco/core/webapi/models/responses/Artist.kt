package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val name: String,
    val url: String,
    val mbid: String? = null,
)
