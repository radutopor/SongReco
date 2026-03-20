package com.radutopor.songreco.features.trending.impl.repository

import com.radutopor.songreco.core.webapi.models.responses.Artists
import com.radutopor.songreco.core.webapi.testdoubles.artistStub
import com.radutopor.songreco.core.webapi.testdoubles.chartGetTopArtistsResponseStub
import com.radutopor.songreco.core.webapi.testdoubles.errorResponseStub
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TrendingWebApiMapperTest {

    private val mapper = TrendingWebApiMapperImpl()

    @Test
    fun `WHEN getTrendingModelLoaded THEN return mapped TrendingModel Loaded`() {
        val response = chartGetTopArtistsResponseStub.copy(
            artists = Artists(
                artist = listOf(
                    artistStub.copy(name = "artist"),
                )
            )
        )
        val model = mapper.getTrendingModelLoaded(response)
        assertEquals(1, model.items.size)
        assertEquals("artist", model.items[0])
    }

    @Test
    fun `WHEN getTrendingModelErrorApi THEN return mapped TrendingModel Error Api`() {
        val response = errorResponseStub.copy(message = "message")
        val model = mapper.getTrendingModelErrorApi(response)
        assertEquals(response.message, model.message)
    }
}
