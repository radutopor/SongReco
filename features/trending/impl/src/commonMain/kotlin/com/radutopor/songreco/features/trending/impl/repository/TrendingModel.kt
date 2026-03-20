package com.radutopor.songreco.features.trending.impl.repository

internal sealed class TrendingModel {

    data class Loaded(
        val items: List<String>,
    ) : TrendingModel()

    sealed class Error(
        val isRetriable: Boolean = false,
    ) : TrendingModel() {

        data class Api(
            val message: String,
        ) : Error()

        object Generic : Error(isRetriable = true)
    }
}
