package com.radutopor.songreco.features.trending.impl.repository

import com.radutopor.songreco.core.webapi.WebApiException
import com.radutopor.songreco.core.webapi.testdoubles.WebApiFake
import com.radutopor.songreco.core.webapi.testdoubles.errorResponseStub
import com.radutopor.songreco.features.trending.impl.testdoubles.repository.TrendingWebApiMapperFake
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertIs

internal class TrendingRepositoryTest {

    private val webApi = WebApiFake()
    private val mapper = TrendingWebApiMapperFake()

    private val repository = TrendingRepositoryImpl(
        webApi = webApi,
        mapper = mapper,
    )

    @Test
    fun `GIVEN generic error WHEN getTrendingModel THEN return TrendingModel Error Generic`() = runTest {
        webApi.chartGetTopArtistsReturn = { throw Exception() }
        val model = repository.getTrendingModel()
        assertIs<TrendingModel.Error.Generic>(model)
    }

    @Test
    fun `GIVEN api error WHEN getTrendingModel THEN return TrendingModel Error Api`() = runTest {
        webApi.chartGetTopArtistsReturn = { throw WebApiException(errorResponseStub) }
        val model = repository.getTrendingModel()
        assertIs<TrendingModel.Error.Api>(model)
    }
}
