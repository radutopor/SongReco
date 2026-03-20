package com.radutopor.songreco.features.details.impl

import com.radutopor.songreco.core.arch.stringvalue.toStringValue
import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.details.impl.repository.DetailsModel
import com.radutopor.songreco.features.list.api.ListArgs
import songreco.core.designsystem.generated.resources.msg_err_generic
import songreco.core.designsystem.generated.resources.Res as CoreRes

internal class DetailsReducerFactory {
    fun get(
        args: DetailsArgs,
        actionSink: (DetailsAction) -> Unit,
    ) = DetailsReducer(args, actionSink)
}

internal class DetailsReducer(
    private val args: DetailsArgs,
    private val actionSink: (DetailsAction) -> Unit,
) {

    val initState = DetailsState(
        onBackClick = { actionSink(DetailsAction.BackClicked) },
        body = DetailsState.Body.Loading,
        upwardRequest = UpwardRequest(
            onRequestConsumed = { actionSink(DetailsAction.UpwardRequestConsumed) },
        ),
    )

    fun DetailsState.reduce(action: DetailsAction): DetailsState =
        when (action) {
            is DetailsAction.DetailsModelAvailable -> withDetailsModel(action.model)
            is DetailsAction.SimilarClicked -> withShowListSimilarRequest()
            is DetailsAction.RetryClicked -> withBodyLoading()
            is DetailsAction.BackClicked -> withPreviousContentRequest()
            is DetailsAction.UpwardRequestConsumed -> withClearedRequest()
        }

    private fun DetailsState.withDetailsModel(model: DetailsModel): DetailsState =
        copy(
            body = when (model) {
                is DetailsModel.Loaded -> getDetailsBodyLoaded(model)
                is DetailsModel.Error -> getDetailsBodyError(model)
            }
        )

    private fun getDetailsBodyLoaded(model: DetailsModel.Loaded): DetailsState.Body.Loaded =
        DetailsState.Body.Loaded(
            albumArtUrl = model.albumArtUrl,
            name = model.name,
            artist = model.artist,
            albumName = model.albumName,
            duration = model.duration,
            onSimilarClick = { actionSink(DetailsAction.SimilarClicked) }
        )

    private fun getDetailsBodyError(model: DetailsModel.Error): DetailsState.Body.Error =
        DetailsState.Body.Error(
            message = when (model) {
                is DetailsModel.Error.Api -> model.message.toStringValue()
                is DetailsModel.Error.Generic -> CoreRes.string.msg_err_generic.toStringValue()
            },
            onRetryClick = { actionSink(DetailsAction.RetryClicked) }.takeIf { model.isRetriable }
        )

    private fun DetailsState.withBodyLoading(): DetailsState =
        copy(body = DetailsState.Body.Loading)

    private fun DetailsState.withShowListSimilarRequest(): DetailsState {
        val listArgs = ListArgs.Similar(
            artist = args.artist,
            track = args.track,
        )
        val request = DetailsState.Request.ShowListContent(listArgs)
        return withUpwardRequest(request)
    }

    private fun DetailsState.withPreviousContentRequest(): DetailsState =
        withUpwardRequest(DetailsState.Request.ShowPreviousContent)

    private fun DetailsState.withClearedRequest(): DetailsState =
        withUpwardRequest(null)

    private fun DetailsState.withUpwardRequest(request: DetailsState.Request?): DetailsState =
        copy(upwardRequest = upwardRequest.copy(request = request))
}
