package com.radutopor.songreco.core.webapi

import com.radutopor.songreco.core.webapi.models.resources.TrackSearchResource
import com.radutopor.songreco.core.webapi.testdoubles.httpclient.HttpClientFake
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal class WebApiTest {

    private val httpClientFake = HttpClientFake()

    val webApi = WebApiImpl(
        httpClient = httpClientFake.client,
    )

    @Test
    fun `GIVEN valid response WHEN making api call THEN return deserialized response`() = runTest {
        httpClientFake.responseContent = """
            {
                "results": {
                    "trackmatches": {
                        "track": []
                    }
                }
            }
        """.trimIndent()
        val response = webApi.trackSearch(TrackSearchResource(track = ""))
        assertTrue(response.results.trackMatches.track.isEmpty())
    }

    @Test
    fun `GIVEN error response WHEN making api call THEN throw WebApiException`() = runTest {
        val errorCode = 123
        val errorMessage = "Error message"
        httpClientFake.responseContent = """
            {
                "error": $errorCode,
                "message": "$errorMessage"
            }
        """.trimIndent()
        val exception = assertFailsWith<WebApiException> {
            webApi.trackSearch(TrackSearchResource(track = ""))
        }
        assertEquals(errorCode, exception.errorResponse.error)
        assertEquals(errorMessage, exception.errorResponse.message)
        assertEquals(errorMessage, exception.message)
    }
}