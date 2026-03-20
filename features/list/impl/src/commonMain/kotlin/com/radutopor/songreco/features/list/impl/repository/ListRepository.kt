package com.radutopor.songreco.features.list.impl.repository

import com.radutopor.songreco.core.webapi.WebApi
import com.radutopor.songreco.core.webapi.WebApiException
import com.radutopor.songreco.features.list.api.ListArgs

internal interface ListRepository {
    suspend fun getListModel(args: ListArgs): ListModel
}

internal class ListRepositoryImpl(
    private val webApi: WebApi,
    private val mapper: ListWebApiMapper,
) : ListRepository {

    override suspend fun getListModel(args: ListArgs): ListModel =
        when (args) {
            is ListArgs.Search -> getTrackSearch(args)
            is ListArgs.Similar -> getSimilarTracks(args)
        }

    private suspend fun getTrackSearch(args: ListArgs.Search): ListModel =
        handleErrors {
            val resource = mapper.getTrackSearchResource(args)
            val response = webApi.trackSearch(resource)
            mapper.getListModelLoaded(response)
        }

    private suspend fun getSimilarTracks(args: ListArgs.Similar): ListModel =
        handleErrors {
            val resource = mapper.getTrackSimilarResource(args)
            val response = webApi.trackGetSimilar(resource)
            mapper.getListModelLoaded(response)
        }

    private suspend fun handleErrors(block: suspend () -> ListModel.Loaded): ListModel =
        try {
            block().takeIf { it.items.isNotEmpty() } ?: ListModel.Error.NoResults
        } catch (e: WebApiException) {
            mapper.getListModelErrorApi(e.errorResponse)
        } catch (e: Exception) {
            ListModel.Error.Generic
        }
}
