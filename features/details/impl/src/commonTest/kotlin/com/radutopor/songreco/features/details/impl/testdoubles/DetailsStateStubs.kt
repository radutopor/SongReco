package com.radutopor.songreco.features.details.impl.testdoubles

import com.radutopor.songreco.core.arch.stringvalue.toStringValue
import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.details.impl.DetailsState

internal val detailsStateBodyLoadedStub = DetailsState.Body.Loaded(
    albumArtUrl = "",
    name = "",
    artist = "",
    albumName = "",
    duration = "",
    onSimilarClick = {},
)

internal val detailsStateBodyErrorStub = DetailsState.Body.Error(
    message = "".toStringValue(),
    onRetryClick = {},
)

internal val detailsStateRequestStub = DetailsState.Request.ShowPreviousContent

internal val detailsStateStub = DetailsState(
    onBackClick = {},
    body = detailsStateBodyLoadedStub,
    upwardRequest = UpwardRequest(
        onRequestConsumed = {},
    ),
)
