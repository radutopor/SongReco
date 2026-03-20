package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class TrackGetInfoResponse(
    val track: TrackInfo
)
