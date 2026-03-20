package com.radutopor.songreco.core.webapi

import com.radutopor.songreco.core.webapi.models.resources.ChartGetTopArtistsResource
import com.radutopor.songreco.core.webapi.models.resources.TrackGetInfoResource
import com.radutopor.songreco.core.webapi.models.resources.TrackGetSimilarResource
import com.radutopor.songreco.core.webapi.models.resources.TrackSearchResource
import com.radutopor.songreco.core.webapi.models.responses.ChartGetTopArtistsResponse
import com.radutopor.songreco.core.webapi.models.responses.ErrorResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackGetInfoResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackGetSimilarResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.serialization.JsonConvertException

interface WebApi {
    suspend fun trackSearch(resource: TrackSearchResource): TrackSearchResponse
    suspend fun trackGetInfo(resource: TrackGetInfoResource): TrackGetInfoResponse
    suspend fun trackGetSimilar(resource: TrackGetSimilarResource): TrackGetSimilarResponse
    suspend fun chartGetTopArtists(resource: ChartGetTopArtistsResource): ChartGetTopArtistsResponse
}

internal class WebApiImpl(
    private val httpClient: HttpClient,
) : WebApi {

    override suspend fun trackSearch(resource: TrackSearchResource): TrackSearchResponse = get(resource)

    override suspend fun trackGetInfo(resource: TrackGetInfoResource): TrackGetInfoResponse = get(resource)

    override suspend fun trackGetSimilar(resource: TrackGetSimilarResource): TrackGetSimilarResponse = get(resource)

    override suspend fun chartGetTopArtists(resource: ChartGetTopArtistsResource): ChartGetTopArtistsResponse = get(resource)

    private suspend inline fun <reified Resource : Any, reified Response> get(resource: Resource): Response =
        httpClient.get(resource).run {
            // Last.fm API returns status 200 for errors, making error checking a bit awkward
            try {
                body<Response>()
            } catch (e: JsonConvertException) {
                throw WebApiException(body<ErrorResponse>())
            }
        }
}
