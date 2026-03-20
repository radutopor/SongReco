package com.radutopor.songreco.core.webapi

import com.radutopor.songreco.core.webapi.httpclient.getLastFmHttpClient
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

private object LastFmHttpClient

val webApiModule = module {

    single<HttpClient>(named<LastFmHttpClient>()) { getLastFmHttpClient() }

    single<WebApi> {
        WebApiImpl(
            httpClient = get(qualifier = named<LastFmHttpClient>()),
        )
    }
}
