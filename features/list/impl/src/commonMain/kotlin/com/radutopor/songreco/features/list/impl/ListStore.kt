package com.radutopor.songreco.features.list.impl

import com.radutopor.songreco.features.list.api.ListArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListStore internal constructor(
    reducerFactory: ListReducerFactory,
    launcherFactory: ListLauncherFactory,
    featureScope: CoroutineScope,
    args: ListArgs,
) {
    private val reducer = reducerFactory.get(::onAction)
    private val launcher = launcherFactory.get(featureScope, args, ::onAction)

    private val _state = MutableStateFlow(reducer.initState(args))
    val state: StateFlow<ListState> = _state.asStateFlow()

    private fun onAction(action: ListAction) {
        _state.value = reducer.run { state.value.reduce(action) }
        launcher.launchSideEffect(action)
    }
}