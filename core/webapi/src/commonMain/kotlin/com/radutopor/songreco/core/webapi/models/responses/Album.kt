package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val artist: String,
    val title: String,
    val url: String,
    val image: List<Image>,
    val mbid: String? = null,
)
