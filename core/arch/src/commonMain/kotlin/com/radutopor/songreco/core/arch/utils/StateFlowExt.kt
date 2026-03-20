package com.radutopor.songreco.core.arch.utils

import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

/**
 * Stolen from a kotlinx.coroutines github issue - https://github.com/Kotlin/kotlinx.coroutines/issues/2631
 * I expect something like this will be implemented natively in a future version of coroutines.flow
 */
@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
fun <T, R> StateFlow<T>.map(transform: (T) -> R): StateFlow<R> = object : StateFlow<R> {

    override val replayCache: List<R> get() = listOf(value)

    override suspend fun collect(collector: FlowCollector<R>): Nothing {
        var lastEmittedValue: Any? = nullSurrogate
        this@map.collect { newValue ->
            val transformedValue = transform(newValue)
            if (transformedValue != lastEmittedValue) {
                lastEmittedValue = transformedValue
                collector.emit(transformedValue)
            }
        }
    }

    private var lastUpstreamValue = this@map.value

    override var value: R = transform(lastUpstreamValue)
        private set
        get() {
            val currentUpstreamValue: T = this@map.value
            if (currentUpstreamValue == lastUpstreamValue) return field
            val newValue = transform(currentUpstreamValue)
            field = newValue
            lastUpstreamValue = currentUpstreamValue
            return newValue
        }
}

private val nullSurrogate = Any()