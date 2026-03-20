package com.radutopor.songreco.features.root.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RootStore internal constructor(
    reducerFactory: RootReducerFactory,
    featureScope: CoroutineScope,
) {
    private val reducer = reducerFactory.get(featureScope, ::onAction)

    private val _state = MutableStateFlow(reducer.initState)
    val state: StateFlow<RootState> = _state.asStateFlow()

    private fun onAction(action: RootAction) {
        _state.value = reducer.run { state.value.reduce(action) }
    }
}