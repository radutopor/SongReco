package com.radutopor.songreco.features.trending.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrendingStore internal constructor(
    reducerFactory: TrendingReducerFactory,
    launcherFactory: TrendingLauncherFactory,
    featureScope: CoroutineScope,
) {
    private val reducer = reducerFactory.get(::onAction)
    private val launcher = launcherFactory.get(featureScope, ::onAction)

    private val _state = MutableStateFlow(reducer.initState)
    val state: StateFlow<TrendingState> = _state.asStateFlow()

    private fun onAction(action: TrendingAction) {
        _state.value = reducer.run { state.value.reduce(action) }
        launcher.launchSideEffect(action)
    }
}