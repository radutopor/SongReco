package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class SimilarTrack(
    val name: String,
    val playcount: Long,
    val match: Double,
    val url: String,
    val duration: Long?,
    val artist: Artist,
    val image: List<Image>,
    val mbid: String? = null
)
