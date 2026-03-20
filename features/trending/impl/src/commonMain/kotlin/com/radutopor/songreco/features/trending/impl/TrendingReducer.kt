package com.radutopor.songreco.features.trending.impl

import com.radutopor.songreco.core.arch.stringvalue.toStringValue
import com.radutopor.songreco.features.trending.impl.repository.TrendingModel
import songreco.core.designsystem.generated.resources.msg_err_generic
import songreco.core.designsystem.generated.resources.Res as CoreRes

internal class TrendingReducerFactory {
    fun get(
        actionSink: (TrendingAction) -> Unit,
    ) = TrendingReducer(actionSink)
}

internal class TrendingReducer(
    private val actionSink: (TrendingAction) -> Unit,
) {

    val initState: TrendingState = TrendingState.Loading

    fun TrendingState.reduce(action: TrendingAction): TrendingState =
        when (action) {
            is TrendingAction.TrendingModelAvailable -> withTrendingModel(action.model)
            is TrendingAction.RetryClicked -> withBodyLoading()
        }

    private fun TrendingState.withTrendingModel(model: TrendingModel): TrendingState =
        when (model) {
            is TrendingModel.Loaded -> getTrendingLoaded(model)
            is TrendingModel.Error -> getTrendingError(model)
        }

    private fun getTrendingLoaded(model: TrendingModel.Loaded): TrendingState.Loaded =
        TrendingState.Loaded(
            items = model.items,
        )

    private fun getTrendingError(model: TrendingModel.Error): TrendingState.Error =
        TrendingState.Error(
            message = when (model) {
                is TrendingModel.Error.Api -> model.message.toStringValue()
                is TrendingModel.Error.Generic -> CoreRes.string.msg_err_generic.toStringValue()
            },
            onRetryClick = { actionSink(TrendingAction.RetryClicked) }.takeIf { model.isRetriable }
        )

    private fun TrendingState.withBodyLoading(): TrendingState =
        TrendingState.Loading
}
