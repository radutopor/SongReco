package com.radutopor.songreco.core.webapi.models.resources

import io.ktor.resources.Resource

@Resource("")
data class TrackGetSimilarResource(
    val method: String = "track.getSimilar",
    val artist: String,
    val track: String,
)
