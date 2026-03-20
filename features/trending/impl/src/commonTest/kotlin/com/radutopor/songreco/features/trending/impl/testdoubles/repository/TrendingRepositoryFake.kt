package com.radutopor.songreco.features.trending.impl.testdoubles.repository

import com.radutopor.songreco.features.trending.impl.repository.TrendingModel
import com.radutopor.songreco.features.trending.impl.repository.TrendingRepository

internal class TrendingRepositoryFake : TrendingRepository {

    var getTrendingModelCalled = false
    var getTrendingModelReturn: () -> TrendingModel = {
        getTrendingModelCalled = true
        trendingModelLoadedStub
    }

    override suspend fun getTrendingModel(): TrendingModel = getTrendingModelReturn()
}
