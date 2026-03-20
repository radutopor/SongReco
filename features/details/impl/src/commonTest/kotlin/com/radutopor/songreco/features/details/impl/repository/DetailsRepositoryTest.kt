package com.radutopor.songreco.features.details.impl.repository

import com.radutopor.songreco.core.webapi.WebApiException
import com.radutopor.songreco.core.webapi.testdoubles.WebApiFake
import com.radutopor.songreco.core.webapi.testdoubles.errorResponseStub
import com.radutopor.songreco.features.details.impl.testdoubles.detailsArgsStub
import com.radutopor.songreco.features.details.impl.testdoubles.repository.DetailsWebApiMapperFake
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertIs

internal class DetailsRepositoryTest {

    private val webApi = WebApiFake()
    private val mapper = DetailsWebApiMapperFake()

    private val repository = DetailsRepositoryImpl(
        webApi = webApi,
        mapper = mapper,
    )

    @Test
    fun `GIVEN generic error WHEN getDetailsModel THEN return DetailsModel Error Generic`() = runTest {
        webApi.trackGetInfoReturn = { throw Exception() }
        val model = repository.getDetailsModel(detailsArgsStub)
        assertIs<DetailsModel.Error.Generic>(model)
    }

    @Test
    fun `GIVEN api error WHEN getDetailsModel THEN return DetailsModel Error Api`() = runTest {
        webApi.trackGetInfoReturn = { throw WebApiException(errorResponseStub) }
        val model = repository.getDetailsModel(detailsArgsStub)
        assertIs<DetailsModel.Error.Api>(model)
    }
}
