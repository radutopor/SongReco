package com.radutopor.songreco.core.webapi.testdoubles.httpclient

import com.radutopor.songreco.core.webapi.httpclient.getLastFmHttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

internal class HttpClientFake {

    var responseContent: String = ""
    var responseCode: HttpStatusCode = HttpStatusCode.Companion.OK

    val client = getLastFmHttpClient(
        engine = MockEngine.Companion {
            respond(
                content = responseContent,
                status = responseCode,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    )
}