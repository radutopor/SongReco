package com.radutopor.songreco.features.trending.impl

import com.radutopor.songreco.features.trending.impl.repository.TrendingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class TrendingLauncherFactory(
    private val repository: TrendingRepository,
) {
    fun get(
        featureScope: CoroutineScope,
        actionSink: (TrendingAction) -> Unit,
    ) = TrendingLauncher(repository, featureScope, actionSink)
}

internal class TrendingLauncher(
    private val repository: TrendingRepository,
    private val featureScope: CoroutineScope,
    private val actionSink: (TrendingAction) -> Unit,
) {

    init {
        launch(::getTrendingModel)
    }

    fun launchSideEffect(action: TrendingAction) =
        when (action) {
            is TrendingAction.RetryClicked -> launch(::getTrendingModel)
            else -> Unit
        }

    private suspend fun getTrendingModel() {
        val trendingModel = repository.getTrendingModel()
        actionSink(TrendingAction.TrendingModelAvailable(trendingModel))
    }

    private fun launch(block: suspend () -> Unit) =
        featureScope.launch { block() }
}
