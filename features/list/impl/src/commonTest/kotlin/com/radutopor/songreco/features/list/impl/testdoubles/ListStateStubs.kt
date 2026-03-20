package com.radutopor.songreco.features.list.impl.testdoubles

import com.radutopor.songreco.core.arch.stringvalue.toStringValue
import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.list.impl.ListState
import songreco.features.list.impl.generated.resources.Res
import songreco.features.list.impl.generated.resources.title_search

internal val listStateHeaderStub = ListState.Header(
    title = Res.string.title_search,
    onBackClick = {},
)

internal val listStateBodyLoadedItem = ListState.Body.Loaded.Item(
    artist = "",
    name = "",
    onClick = {},
)

internal val listStateBodyLoaded = ListState.Body.Loaded(
    items = listOf(listStateBodyLoadedItem)
)

internal val listStateBodyError = ListState.Body.Error(
    message = "".toStringValue(),
    onRetryClick = {},
)

internal val listStateRequestStub = ListState.Request.ShowPreviousContent

internal val listStateStub = ListState(
    header = listStateHeaderStub,
    body = listStateBodyLoaded,
    upwardRequest = UpwardRequest(
        onRequestConsumed = {},
    )
)