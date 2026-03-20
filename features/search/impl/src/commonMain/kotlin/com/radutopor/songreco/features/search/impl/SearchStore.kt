package com.radutopor.songreco.features.search.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchStore internal constructor(
    reducerFactory: SearchReducerFactory,
) {
    private val reducer = reducerFactory.get(::onAction)

    private val _state = MutableStateFlow(reducer.initState)
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private fun onAction(action: SearchAction) {
        _state.value = reducer.run { state.value.reduce(action) }
    }
}