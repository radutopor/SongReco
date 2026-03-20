package com.radutopor.songreco.features.list.impl

import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.list.impl.repository.ListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class ListLauncherFactory(
    private val repository: ListRepository,
) {
    fun get(
        featureScope: CoroutineScope,
        args: ListArgs,
        actionSink: (ListAction) -> Unit,
    ) = ListLauncher(repository, featureScope, args, actionSink)
}

internal class ListLauncher(
    private val repository: ListRepository,
    private val featureScope: CoroutineScope,
    private val args: ListArgs,
    private val actionSink: (ListAction) -> Unit,
) {

    init {
        launch(::getListModel)
    }

    fun launchSideEffect(action: ListAction) =
        when (action) {
            is ListAction.RetryClicked -> launch(::getListModel)
            else -> Unit
        }

    private suspend fun getListModel() {
        val listModel = repository.getListModel(args)
        actionSink(ListAction.ListModelAvailable(listModel))
    }

    private fun launch(block: suspend () -> Unit) =
        featureScope.launch { block() }
}