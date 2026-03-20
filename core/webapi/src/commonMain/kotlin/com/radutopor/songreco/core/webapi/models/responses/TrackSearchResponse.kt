package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackSearchResponse(
    val results: TrackSearchResults
)

@Serializable
data class TrackSearchResults(
    @SerialName("trackmatches") val trackMatches: TrackMatches
)

@Serializable
data class TrackMatches(
    val track: List<TrackSearchResult>
)
