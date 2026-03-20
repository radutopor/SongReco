package com.radutopor.songreco.core.arch.substatestack

/**
 * Composed in states to model a stack of observed substates.
 */
data class SubstateStack<Substate>(
    val current: Substate,
    val backstack: List<SubstateStackItem<Substate>>,
)