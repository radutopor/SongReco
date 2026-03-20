package com.radutopor.songreco.features.details.impl.repository

import com.radutopor.songreco.core.webapi.WebApi
import com.radutopor.songreco.core.webapi.WebApiException
import com.radutopor.songreco.features.details.api.DetailsArgs

internal interface DetailsRepository {
    suspend fun getDetailsModel(args: DetailsArgs): DetailsModel
}

internal class DetailsRepositoryImpl(
    private val webApi: WebApi,
    private val mapper: DetailsWebApiMapper,
) : DetailsRepository {

    override suspend fun getDetailsModel(args: DetailsArgs): DetailsModel =
        handleErrors {
            val resource = mapper.getTrackInfoResource(args)
            val response = webApi.trackGetInfo(resource)
            mapper.getDetailsModelLoaded(response)
        }

    private suspend fun handleErrors(block: suspend () -> DetailsModel.Loaded): DetailsModel =
        try {
            block()
        } catch (e: WebApiException) {
            mapper.getDetailsModelErrorApi(e.errorResponse)
        } catch (e: Exception) {
            DetailsModel.Error.Generic
        }
}
