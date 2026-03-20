package com.radutopor.songreco.core.arch.substatestack

import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

/**
 * Constructs an [SubstateStack] from the given [SubstateStackItem], setting it as the current and starts observing it.
 * @return a constructed [SubstateStack] object
 */
fun <Substate> SubstateStackItem<Substate>.toSubstateStack(
    onNewSubstate: (Substate) -> Unit,
): SubstateStack<Substate> =
    getSubstateStack(listOf(this), onNewSubstate)

/**
 * Given this [SubstateStack], empties the observation scope of the current top [SubstateStackItem] if any,
 * pushes the new [SubstateStackItem] on top of the stack and starts observing it, and returns a new [SubstateStack] object.
 * @return a new [SubstateStack] object
 */
fun <Substate> SubstateStack<Substate>.push(
    substateItem: SubstateStackItem<Substate>,
    onNewSubstate: (Substate) -> Unit,
): SubstateStack<Substate> {
    // Cancel observation of the current top substate, and any of its intrinsic jobs, but keep its scope alive for future re-observation.
    backstack.lastOrNull()?.observationScope?.coroutineContext?.cancelChildren()
    return getSubstateStack(backstack.plus(substateItem), onNewSubstate)
}

/**
 * Given this [SubstateStack], if there are multiple [SubstateStackItem] in the stack, cancels the observation scope of top one,
 * pops it off the stack, and returns a new [SubstateStack] object.
 * @return a new [SubstateStack] object if any [SubstateStackItem] was popped, otherwise [this]
 */
fun <Substate> SubstateStack<Substate>.pop(
    onNewSubstate: (Substate) -> Unit,
): SubstateStack<Substate> {
    if (backstack.size <= 1) return this
    // Cancel observation of the current top substate, any of its intrinsic jobs, and its scope definitively.
    backstack.last().observationScope.cancel()
    return getSubstateStack(backstack.dropLast(1), onNewSubstate)
}

/**
 * Given this [SubstateStack], cancels the observation scope of all [SubstateStackItem] currently in the stack and removes them,
 * places the new [SubstateStackItem] in the stack and starts observing it, returning a new [SubstateStack] object.
 * @return a new [SubstateStack] object
 */
fun <Substate> SubstateStack<Substate>.replace(
    substateItem: SubstateStackItem<Substate>,
    onNewSubstate: (Substate) -> Unit,
): SubstateStack<Substate> {
    // Cancel observation of all substate in the stack, any of their intrinsic jobs, and their scopes definitively.
    backstack.forEach { it.observationScope.cancel() }
    return getSubstateStack(listOf(substateItem), onNewSubstate)
}

private fun <Substate> getSubstateStack(
    backstack: List<SubstateStackItem<Substate>>,
    onNewSubstate: (Substate) -> Unit,
): SubstateStack<Substate> {
    val topSubstate = backstack.last()
    // Start observing top substate
    topSubstate.observationScope.launch {
        topSubstate.observableState
            .drop(1)    // Skip the initial value (set explicitly in the state below) and trigger only on future emissions
            .collect(onNewSubstate)
    }
    return SubstateStack(
        current = topSubstate.observableState.value,
        backstack = backstack,
    )
}
