package com.radutopor.songreco.features.list.impl.repository

import com.radutopor.songreco.core.webapi.models.responses.SimilarTracks
import com.radutopor.songreco.core.webapi.models.responses.TrackMatches
import com.radutopor.songreco.core.webapi.models.responses.TrackSearchResults
import com.radutopor.songreco.core.webapi.testdoubles.artistStub
import com.radutopor.songreco.core.webapi.testdoubles.errorResponseStub
import com.radutopor.songreco.core.webapi.testdoubles.similarTrackStub
import com.radutopor.songreco.core.webapi.testdoubles.trackGetSimilarResponseStub
import com.radutopor.songreco.core.webapi.testdoubles.trackSearchResponseStub
import com.radutopor.songreco.core.webapi.testdoubles.trackSearchResultStub
import com.radutopor.songreco.features.list.impl.testdoubles.listArgsSearchStub
import com.radutopor.songreco.features.list.impl.testdoubles.listArgsSimilarStub
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ListWebApiMapperTest {

    private val mapper = ListWebApiMapperImpl()

    @Test
    fun `WHEN getTrackSearchResource THEN return mapped TrackSearchResource`() {
        val args = listArgsSearchStub.copy(track = "track")
        val resource = mapper.getTrackSearchResource(args)
        assertEquals(args.track, resource.track)
    }

    @Test
    fun `WHEN getTrackSimilarResource THEN return mapped TrackGetSimilarResource`() {
        val args = listArgsSimilarStub.copy(
            artist = "artist",
            track = "track",
        )
        val resource = mapper.getTrackSimilarResource(args)
        assertEquals(args.artist, resource.artist)
        assertEquals(args.track, resource.track)
    }

    @Test
    fun `WHEN getListModelLoaded from TrackSearchResponse THEN return mapped ListModel Loaded`() {
        val response = trackSearchResponseStub.copy(
            results = TrackSearchResults(
                trackMatches = TrackMatches(
                    track = listOf(
                        trackSearchResultStub.copy(
                            name = "name",
                            artist = "artist",
                        )
                    )
                )
            )
        )
        val model = mapper.getListModelLoaded(response)
        assertEquals(1, model.items.size)
        assertEquals("artist", model.items[0].artist)
        assertEquals("name", model.items[0].name)
    }

    @Test
    fun `WHEN getListModelLoaded from TrackGetSimilarResponse THEN return mapped ListModel Loaded`() {
        val response = trackGetSimilarResponseStub.copy(
            similarTracks = SimilarTracks(
                track = listOf(
                    similarTrackStub.copy(
                        name = "name",
                        artist = artistStub.copy(name = "artist"),
                    )
                )
            )
        )
        val model = mapper.getListModelLoaded(response)
        assertEquals(1, model.items.size)
        assertEquals("artist", model.items[0].artist)
        assertEquals("name", model.items[0].name)
    }

    @Test
    fun `WHEN getListModelErrorApi THEN return mapped ListModel Error Api`() {
        val response = errorResponseStub.copy(message = "message")
        val model = mapper.getListModelErrorApi(response)
        assertEquals(response.message, model.message)
    }
}
