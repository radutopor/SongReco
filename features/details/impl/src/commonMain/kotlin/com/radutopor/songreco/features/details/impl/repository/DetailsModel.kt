package com.radutopor.songreco.features.details.impl.repository

internal sealed class DetailsModel {

    data class Loaded(
        val artist: String,
        val name: String,
        val duration: String,
        val albumArtUrl: String,
        val albumName: String,
    ) : DetailsModel()

    sealed class Error(
        val isRetriable: Boolean = false,
    ) : DetailsModel() {

        data class Api(
            val message: String,
        ) : Error()

        object Generic : Error(isRetriable = true)
    }
}