package com.radutopor.songreco.features.trending.impl.repository

import com.radutopor.songreco.core.webapi.models.resources.ChartGetTopArtistsResource
import com.radutopor.songreco.core.webapi.models.responses.ChartGetTopArtistsResponse
import com.radutopor.songreco.core.webapi.models.responses.ErrorResponse

internal interface TrendingWebApiMapper {
    fun getTrendingResource(): ChartGetTopArtistsResource
    fun getTrendingModelLoaded(response: ChartGetTopArtistsResponse): TrendingModel.Loaded
    fun getTrendingModelErrorApi(response: ErrorResponse): TrendingModel.Error.Api
}

internal class TrendingWebApiMapperImpl : TrendingWebApiMapper {

    //region from Args to Resources

    override fun getTrendingResource(): ChartGetTopArtistsResource =
        ChartGetTopArtistsResource()

    // endregion

    //region from Responses to Model

    override fun getTrendingModelLoaded(response: ChartGetTopArtistsResponse): TrendingModel.Loaded =
        TrendingModel.Loaded(
            items = response.artists.artist.map { it.name }
        )

    override fun getTrendingModelErrorApi(response: ErrorResponse): TrendingModel.Error.Api =
        TrendingModel.Error.Api(
            message = response.message,
        )

    // endregion
}
