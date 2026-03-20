package com.radutopor.songreco.features.details.impl

import com.radutopor.songreco.features.details.api.DetailsArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsStore internal constructor(
    reducerFactory: DetailsReducerFactory,
    launcherFactory: DetailsLauncherFactory,
    featureScope: CoroutineScope,
    args: DetailsArgs,
) {
    private val reducer = reducerFactory.get(args, ::onAction)
    private val launcher = launcherFactory.get(featureScope, args, ::onAction)

    private val _state = MutableStateFlow(reducer.initState)
    val state: StateFlow<DetailsState> = _state.asStateFlow()

    private fun onAction(action: DetailsAction) {
        _state.value = reducer.run { state.value.reduce(action) }
        launcher.launchSideEffect(action)
    }
}
