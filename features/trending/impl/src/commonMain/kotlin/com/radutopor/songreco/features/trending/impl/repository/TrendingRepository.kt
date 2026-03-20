package com.radutopor.songreco.features.trending.impl.repository

import com.radutopor.songreco.core.webapi.WebApi
import com.radutopor.songreco.core.webapi.WebApiException

internal interface TrendingRepository {
    suspend fun getTrendingModel(): TrendingModel
}

internal class TrendingRepositoryImpl(
    private val webApi: WebApi,
    private val mapper: TrendingWebApiMapper,
) : TrendingRepository {

    override suspend fun getTrendingModel(): TrendingModel =
        handleErrors {
            val resource = mapper.getTrendingResource()
            val response = webApi.chartGetTopArtists(resource)
            mapper.getTrendingModelLoaded(response)
        }

    private suspend fun handleErrors(block: suspend () -> TrendingModel.Loaded): TrendingModel =
        try {
            block()
        } catch (e: WebApiException) {
            mapper.getTrendingModelErrorApi(e.errorResponse)
        } catch (e: Exception) {
            TrendingModel.Error.Generic
        }
}
