package com.radutopor.songreco.core.arch.substatestack

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

/**
 * Composed in [SubstateStack] to model an observable substate.
 */
data class SubstateStackItem<Substate>(
    val observableState: StateFlow<Substate>,
    val observationScope: CoroutineScope,
)
