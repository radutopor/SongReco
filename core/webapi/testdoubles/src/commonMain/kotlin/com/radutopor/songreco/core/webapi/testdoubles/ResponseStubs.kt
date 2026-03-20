package com.radutopor.songreco.core.webapi.testdoubles

import com.radutopor.songreco.core.webapi.models.responses.Album
import com.radutopor.songreco.core.webapi.models.responses.Artist
import com.radutopor.songreco.core.webapi.models.responses.Artists
import com.radutopor.songreco.core.webapi.models.responses.ChartGetTopArtistsResponse
import com.radutopor.songreco.core.webapi.models.responses.ErrorResponse
import com.radutopor.songreco.core.webapi.models.responses.Image
import com.radutopor.songreco.core.webapi.models.responses.SimilarTrack
import com.radutopor.songreco.core.webapi.models.responses.SimilarTracks
import com.radutopor.songreco.core.webapi.models.responses.Tag
import com.radutopor.songreco.core.webapi.models.responses.TopTags
import com.radutopor.songreco.core.webapi.models.responses.TrackGetInfoResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackGetSimilarResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackInfo
import com.radutopor.songreco.core.webapi.models.responses.TrackMatches
import com.radutopor.songreco.core.webapi.models.responses.TrackSearchResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackSearchResult
import com.radutopor.songreco.core.webapi.models.responses.TrackSearchResults

/**
 * Stubs for web API response entities, to .copy() and reuse in feature tests
 */

val imageStub = Image(
    text = "",
    size = "",
)

val trackSearchResultStub = TrackSearchResult(
    name = "",
    artist = "",
    url = "",
    listeners = "",
    image = listOf(imageStub),
    mbid = null,
)

val trackSearchResponseStub = TrackSearchResponse(
    results = TrackSearchResults(
        trackMatches = TrackMatches(
            track = listOf(trackSearchResultStub),
        ),
    ),
)

val artistStub = Artist(
    name = "",
    url = "",
    mbid = null,
)

val albumStub = Album(
    artist = "",
    title = "",
    url = "",
    image = listOf(imageStub),
    mbid = null,
)

val tagStub = Tag(
    name = "",
    url = "",
)

val trackInfoStub = TrackInfo(
    name = "",
    url = "",
    duration = 0,
    listeners = 0,
    playcount = 0,
    artist = artistStub,
    album = albumStub,
    mbid = null,
    topTags = TopTags(
        tag = listOf(tagStub),
    ),
)

val trackGetInfoResponseStub = TrackGetInfoResponse(
    track = trackInfoStub,
)

val similarTrackStub = SimilarTrack(
    name = "",
    playcount = 0,
    match = 0.0,
    url = "",
    duration = 0,
    artist = artistStub,
    image = listOf(imageStub),
    mbid = null,
)

val trackGetSimilarResponseStub = TrackGetSimilarResponse(
    similarTracks = SimilarTracks(
        track = listOf(similarTrackStub),
    )
)

val chartGetTopArtistsResponseStub = ChartGetTopArtistsResponse(
    artists = Artists(
        artist = listOf(artistStub),
    )
)

val errorResponseStub = ErrorResponse(
    error = 0,
    message = "",
)