package com.radutopor.songreco.features.trending.impl.testdoubles.repository

import com.radutopor.songreco.features.trending.impl.repository.TrendingModel

internal val trendingModelLoadedStub = TrendingModel.Loaded(
    items = listOf("")
)

internal val trendingModelErrorApiStub = TrendingModel.Error.Api(
    message = "",
)
