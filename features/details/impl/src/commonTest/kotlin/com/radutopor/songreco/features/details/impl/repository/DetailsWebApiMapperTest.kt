package com.radutopor.songreco.features.details.impl.repository

import com.radutopor.songreco.core.webapi.testdoubles.albumStub
import com.radutopor.songreco.core.webapi.testdoubles.artistStub
import com.radutopor.songreco.core.webapi.testdoubles.errorResponseStub
import com.radutopor.songreco.core.webapi.testdoubles.imageStub
import com.radutopor.songreco.core.webapi.testdoubles.trackGetInfoResponseStub
import com.radutopor.songreco.core.webapi.testdoubles.trackInfoStub
import com.radutopor.songreco.features.details.impl.testdoubles.detailsArgsStub
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DetailsWebApiMapperTest {

    private val mapper = DetailsWebApiMapperImpl()

    @Test
    fun `WHEN getTrackInfoResource THEN return mapped TrackGetInfoResource`() {
        val args = detailsArgsStub.copy(
            artist = "artist",
            track = "track",
        )
        val resource = mapper.getTrackInfoResource(args)
        assertEquals(args.artist, resource.artist)
        assertEquals(args.track, resource.track)
    }

    @Test
    fun `WHEN getDetailsModelLoaded THEN return mapped DetailsModel Loaded`() {
        val response = trackGetInfoResponseStub.copy(
            track = trackInfoStub.copy(
                artist = artistStub.copy(name = "artist"),
                name = "name",
                duration = 185000, // 3:05
                album = albumStub.copy(
                    image = listOf(imageStub.copy(text = "url")),
                    title = "album",
                )
            )
        )
        val model = mapper.getDetailsModelLoaded(response)
        assertEquals("artist", model.artist)
        assertEquals("name", model.name)
        assertEquals("3:05", model.duration)
        assertEquals("url", model.albumArtUrl)
        assertEquals("album", model.albumName)
    }

    @Test
    fun `WHEN getDetailsModelErrorApi THEN return mapped DetailsModel Error Api`() {
        val response = errorResponseStub.copy(message = "message")
        val model = mapper.getDetailsModelErrorApi(response)
        assertEquals(response.message, model.message)
    }
}
