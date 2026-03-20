package com.radutopor.songreco.features.list.impl

import com.radutopor.songreco.core.arch.stringvalue.toStringValue
import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.list.impl.repository.ListModel
import songreco.core.designsystem.generated.resources.msg_err_generic
import songreco.features.list.impl.generated.resources.Res
import songreco.features.list.impl.generated.resources.msg_err_no_results
import songreco.features.list.impl.generated.resources.title_search
import songreco.features.list.impl.generated.resources.title_similar
import songreco.core.designsystem.generated.resources.Res as CoreRes

internal class ListReducerFactory {
    fun get(
        actionSink: (ListAction) -> Unit,
    ) = ListReducer(actionSink)
}

internal class ListReducer(
    private val actionSink: (ListAction) -> Unit,
) {

    fun initState(args: ListArgs) = ListState(
        header = ListState.Header(
            title = when (args) {
                is ListArgs.Search -> Res.string.title_search
                is ListArgs.Similar -> Res.string.title_similar
            },
            onBackClick = { actionSink(ListAction.BackClicked) }
        ),
        body = ListState.Body.Loading,
        upwardRequest = UpwardRequest(
            onRequestConsumed = { actionSink(ListAction.UpwardRequestConsumed) },
        ),
    )

    fun ListState.reduce(action: ListAction): ListState =
        when (action) {
            is ListAction.ListModelAvailable -> withListModel(action.model)
            is ListAction.ListItemClicked -> withShowDetailsRequest(action.item)
            is ListAction.RetryClicked -> withBodyLoading()
            is ListAction.BackClicked -> withPreviousContentRequest()
            is ListAction.UpwardRequestConsumed -> withClearedRequest()
        }

    private fun ListState.withListModel(model: ListModel): ListState =
        copy(
            body = when (model) {
                is ListModel.Loaded -> getListBodyLoaded(model)
                is ListModel.Error -> getListBodyError(model)
            }
        )

    private fun getListBodyLoaded(model: ListModel.Loaded): ListState.Body.Loaded =
        ListState.Body.Loaded(
            items = model.items.map { itemModel ->
                ListState.Body.Loaded.Item(
                    artist = itemModel.artist,
                    name = itemModel.name,
                    onClick = { actionSink(ListAction.ListItemClicked(itemModel)) },
                )
            },
        )

    private fun getListBodyError(model: ListModel.Error): ListState.Body.Error =
        ListState.Body.Error(
            message = when (model) {
                is ListModel.Error.NoResults -> Res.string.msg_err_no_results.toStringValue()
                is ListModel.Error.Api -> model.message.toStringValue()
                is ListModel.Error.Generic -> CoreRes.string.msg_err_generic.toStringValue()
            },
            onRetryClick = { actionSink(ListAction.RetryClicked) }.takeIf { model.isRetriable }
        )

    private fun ListState.withBodyLoading(): ListState =
        copy(body = ListState.Body.Loading)

    private fun ListState.withShowDetailsRequest(item: ListModel.Loaded.Item): ListState {
        val detailsArgs = DetailsArgs(
            artist = item.artist,
            track = item.name,
        )
        val request = ListState.Request.ShowDetailsContent(detailsArgs)
        return withUpwardRequest(request)
    }

    private fun ListState.withPreviousContentRequest(): ListState =
        withUpwardRequest(ListState.Request.ShowPreviousContent)

    private fun ListState.withClearedRequest(): ListState =
        withUpwardRequest(null)

    private fun ListState.withUpwardRequest(request: ListState.Request?): ListState =
        copy(upwardRequest = upwardRequest.copy(request = request))
}
