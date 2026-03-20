package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChartGetTopArtistsResponse(
    @SerialName("artists") val artists: Artists
)

@Serializable
data class Artists(
    val artist: List<Artist>
)
