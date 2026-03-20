package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class TrackSearchResult(
    val name: String,
    val artist: String,
    val url: String,
    val listeners: String,
    val image: List<Image>,
    val mbid: String? = null,
)
