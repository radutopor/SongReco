package com.radutopor.songreco.core.webapi.httpclient

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun platformHttpEngine(): HttpClientEngine = OkHttp.create {
    // any platform-specific OkHttp config
}
