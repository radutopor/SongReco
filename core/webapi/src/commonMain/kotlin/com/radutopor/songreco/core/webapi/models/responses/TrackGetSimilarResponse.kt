package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackGetSimilarResponse(
    @SerialName("similartracks") val similarTracks: SimilarTracks
)

@Serializable
data class SimilarTracks(
    val track: List<SimilarTrack>
)
