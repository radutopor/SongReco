package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackInfo(
    val name: String,
    val url: String,
    val duration: Long,
    val listeners: Long,
    val playcount: Long,
    val artist: Artist,
    val album: Album,
    val mbid: String? = null,
    @SerialName("toptags") val topTags: TopTags,
)
