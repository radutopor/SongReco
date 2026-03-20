package com.radutopor.songreco.core.webapi.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("#text") val text: String,
    val size: String
)
