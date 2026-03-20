package com.radutopor.songreco.features.trending.impl.testdoubles

import com.radutopor.songreco.core.arch.stringvalue.toStringValue
import com.radutopor.songreco.features.trending.impl.TrendingState

internal val trendingStateLoadedStub = TrendingState.Loaded(
    items = listOf("")
)

internal val trendingStateErrorStub = TrendingState.Error(
    message = "".toStringValue(),
    onRetryClick = {},
)
