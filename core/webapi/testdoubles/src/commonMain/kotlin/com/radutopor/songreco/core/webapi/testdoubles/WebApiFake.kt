package com.radutopor.songreco.core.webapi.testdoubles

import com.radutopor.songreco.core.webapi.WebApi
import com.radutopor.songreco.core.webapi.models.resources.ChartGetTopArtistsResource
import com.radutopor.songreco.core.webapi.models.resources.TrackGetInfoResource
import com.radutopor.songreco.core.webapi.models.resources.TrackGetSimilarResource
import com.radutopor.songreco.core.webapi.models.resources.TrackSearchResource
import com.radutopor.songreco.core.webapi.models.responses.ChartGetTopArtistsResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackGetInfoResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackGetSimilarResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackSearchResponse

/**
 * Fake for WebApi, to reuse in feature tests
 */
class WebApiFake : WebApi {

    var trackSearchArg: TrackSearchResource? = null
    var trackSearchReturn: (TrackSearchResource) -> TrackSearchResponse = {
        trackSearchArg = it
        trackSearchResponseStub
    }
    override suspend fun trackSearch(resource: TrackSearchResource) = trackSearchReturn(resource)

    var trackGetInfoArg: TrackGetInfoResource? = null
    var trackGetInfoReturn: (TrackGetInfoResource) -> TrackGetInfoResponse = {
        trackGetInfoArg = it
        trackGetInfoResponseStub
    }
    override suspend fun trackGetInfo(resource: TrackGetInfoResource) = trackGetInfoReturn(resource)

    var trackGetSimilarArg: TrackGetSimilarResource? = null
    var trackGetSimilarReturn: (TrackGetSimilarResource) -> TrackGetSimilarResponse = {
        trackGetSimilarArg = it
        trackGetSimilarResponseStub
    }
    override suspend fun trackGetSimilar(resource: TrackGetSimilarResource) = trackGetSimilarReturn(resource)

    var chartGetTopArtistsArg: ChartGetTopArtistsResource? = null
    var chartGetTopArtistsReturn: (ChartGetTopArtistsResource) -> ChartGetTopArtistsResponse = {
        chartGetTopArtistsArg = it
        chartGetTopArtistsResponseStub
    }
    override suspend fun chartGetTopArtists(resource: ChartGetTopArtistsResource) = chartGetTopArtistsReturn(resource)
}
