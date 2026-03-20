package com.radutopor.songreco.core.webapi.models.resources

import io.ktor.resources.Resource

@Resource("")
data class TrackGetInfoResource(
    val method: String = "track.getInfo",
    val artist: String,
    val track: String,
)
