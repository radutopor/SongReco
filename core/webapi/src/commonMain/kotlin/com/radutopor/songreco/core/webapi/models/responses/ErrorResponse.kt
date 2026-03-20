package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: Int,
    val message: String,
)
