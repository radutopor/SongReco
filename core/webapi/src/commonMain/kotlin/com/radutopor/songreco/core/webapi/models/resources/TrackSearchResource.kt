package com.radutopor.songreco.core.webapi.models.resources

import io.ktor.resources.Resource

@Resource("")
data class TrackSearchResource(
    val method: String = "track.search",
    val track: String,
)
