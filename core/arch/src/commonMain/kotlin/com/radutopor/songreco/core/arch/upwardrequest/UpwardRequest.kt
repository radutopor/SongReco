package com.radutopor.songreco.core.arch.upwardrequest

/**
 * Composed in substates to model persistent requests to their parent.
 */
data class UpwardRequest<Request>(
    val request: Request? = null,
    val onRequestConsumed: () -> Unit,
)

/**
 * If a request is present, run [requestConsumer] and return its result, while also invoking [UpwardRequest.onRequestConsumed]
 * Otherwise, return null.
 */
fun <Request, R> UpwardRequest<Request>.consume(requestConsumer: (Request) -> R): R? =
    request?.let { requestConsumer(it) }?.also { onRequestConsumed() }