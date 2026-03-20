package com.radutopor.songreco.features.details.impl

import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.details.impl.repository.DetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class DetailsLauncherFactory(
    private val repository: DetailsRepository,
) {
    fun get(
        featureScope: CoroutineScope,
        args: DetailsArgs,
        actionSink: (DetailsAction) -> Unit,
    ) = DetailsLauncher(repository, featureScope, args, actionSink)
}

internal class DetailsLauncher(
    private val repository: DetailsRepository,
    private val featureScope: CoroutineScope,
    private val args: DetailsArgs,
    private val actionSink: (DetailsAction) -> Unit,
) {

    init {
        launch(::getDetailsModel)
    }

    fun launchSideEffect(action: DetailsAction) =
        when (action) {
            is DetailsAction.RetryClicked -> launch(::getDetailsModel)
            else -> Unit
        }

    private suspend fun getDetailsModel() {
        val detailsModel = repository.getDetailsModel(args)
        actionSink(DetailsAction.DetailsModelAvailable(detailsModel))
    }

    private fun launch(block: suspend () -> Unit) =
        featureScope.launch { block() }
}