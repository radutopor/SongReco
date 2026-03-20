package com.radutopor.songreco.features.trending.impl.testdoubles.repository

import com.radutopor.songreco.core.webapi.models.resources.ChartGetTopArtistsResource
import com.radutopor.songreco.core.webapi.models.responses.ChartGetTopArtistsResponse
import com.radutopor.songreco.core.webapi.models.responses.ErrorResponse
import com.radutopor.songreco.core.webapi.testdoubles.chartGetTopArtistsResourceStub
import com.radutopor.songreco.features.trending.impl.repository.TrendingModel
import com.radutopor.songreco.features.trending.impl.repository.TrendingWebApiMapper

internal class TrendingWebApiMapperFake : TrendingWebApiMapper {

    var getTrendingResourceReturn: () -> ChartGetTopArtistsResource = { chartGetTopArtistsResourceStub }
    override fun getTrendingResource(): ChartGetTopArtistsResource = getTrendingResourceReturn()

    var getTrendingModelLoadedReturn: (ChartGetTopArtistsResponse) -> TrendingModel.Loaded = { trendingModelLoadedStub }
    override fun getTrendingModelLoaded(response: ChartGetTopArtistsResponse): TrendingModel.Loaded = getTrendingModelLoadedReturn(response)

    var getTrendingModelErrorApiReturn: (ErrorResponse) -> TrendingModel.Error.Api = { trendingModelErrorApiStub }
    override fun getTrendingModelErrorApi(response: ErrorResponse): TrendingModel.Error.Api = getTrendingModelErrorApiReturn(response)
}
