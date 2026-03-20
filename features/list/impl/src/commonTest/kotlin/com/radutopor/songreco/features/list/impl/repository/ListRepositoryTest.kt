package com.radutopor.songreco.features.list.impl.repository

import com.radutopor.songreco.core.webapi.WebApiException
import com.radutopor.songreco.core.webapi.testdoubles.WebApiFake
import com.radutopor.songreco.core.webapi.testdoubles.errorResponseStub
import com.radutopor.songreco.features.list.impl.testdoubles.listArgsSearchStub
import com.radutopor.songreco.features.list.impl.testdoubles.listArgsSimilarStub
import com.radutopor.songreco.features.list.impl.testdoubles.repository.ListWebApiMapperFake
import com.radutopor.songreco.features.list.impl.testdoubles.repository.listModelLoadedStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull

internal class ListRepositoryTest {

    private val webApi = WebApiFake()
    private val mapper = ListWebApiMapperFake()

    private val repository = ListRepositoryImpl(
        webApi = webApi,
        mapper = mapper,
    )

    @Test
    fun `GIVEN Search args WHEN getListModel THEN call webApi trackSearch`() = runTest {
        repository.getListModel(listArgsSearchStub)
        assertNotNull(webApi.trackSearchArg)
    }

    @Test
    fun `GIVEN Similar args WHEN getListModel THEN call webApi trackGetSimilar`() = runTest {
        repository.getListModel(listArgsSimilarStub)
        assertNotNull(webApi.trackGetSimilarArg)
    }

    @Test
    fun `GIVEN generic error WHEN getListModel THEN return ListModel Error Generic`() = runTest {
        webApi.trackSearchReturn = { throw Exception() }
        val model = repository.getListModel(listArgsSearchStub)
        assertIs<ListModel.Error.Generic>(model)
    }

    @Test
    fun `GIVEN api error WHEN getListModel THEN return ListModel Error Api`() = runTest {
        webApi.trackSearchReturn = { throw WebApiException(errorResponseStub) }
        val model = repository.getListModel(listArgsSearchStub)
        assertIs<ListModel.Error.Api>(model)
    }

    @Test
    fun `GIVEN no results WHEN getListModel THEN return ListModel Error NoResults`() = runTest {
        mapper.searchGetListModelLoadedReturn = { listModelLoadedStub.copy(items = emptyList()) }
        val model = repository.getListModel(listArgsSearchStub)
        assertIs<ListModel.Error.NoResults>(model)
    }
}