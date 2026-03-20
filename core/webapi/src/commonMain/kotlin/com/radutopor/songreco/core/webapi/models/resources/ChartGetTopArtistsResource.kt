package com.radutopor.songreco.core.webapi.models.resources

import io.ktor.resources.Resource

@Resource("")
data class ChartGetTopArtistsResource(
    val method: String = "chart.getTopArtists",
)
