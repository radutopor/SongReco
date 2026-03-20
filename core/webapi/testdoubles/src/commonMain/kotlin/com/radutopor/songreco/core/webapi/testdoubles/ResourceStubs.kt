package com.radutopor.songreco.core.webapi.testdoubles

import com.radutopor.songreco.core.webapi.models.resources.ChartGetTopArtistsResource
import com.radutopor.songreco.core.webapi.models.resources.TrackGetInfoResource
import com.radutopor.songreco.core.webapi.models.resources.TrackGetSimilarResource
import com.radutopor.songreco.core.webapi.models.resources.TrackSearchResource

/**
 * Stubs for web API resource entities, to .copy() and reuse in feature tests
 */

val trackSearchResourceStub = TrackSearchResource(
    track = "",
)

val trackGetSimilarResourceStub = TrackGetSimilarResource(
    artist = "",
    track = "",
)

val trackGetInfoResourceStub = TrackGetInfoResource(
    artist = "",
    track = "",
)

val chartGetTopArtistsResourceStub = ChartGetTopArtistsResource()