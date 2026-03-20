package com.radutopor.songreco.features.trending.impl

import com.radutopor.songreco.core.arch.stringvalue.StringValue

sealed class TrendingState {
    object Loading : TrendingState()

    data class Loaded(
        val items: List<String>,
    ) : TrendingState()

    data class Error(
        val message: StringValue,
        val onRetryClick: (() -> Unit)?,
    ) : TrendingState()
}
