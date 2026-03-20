package com.radutopor.songreco.features.search.impl.testdoubles

import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.search.impl.SearchState

internal val searchStateSearchBoxStub = SearchState.SearchBox(
    text = "",
    onTextChange = {},
)

internal val searchStateSearchButtonStub = SearchState.SearchButton(
    enabled = true,
    onClick = {},
)

internal val searchStateRequestStub = SearchState.Request.ShowListContent(
    args = ListArgs.Search(track = "")
)

internal val searchStateStub = SearchState(
    searchBox = searchStateSearchBoxStub,
    searchButton = searchStateSearchButtonStub,
    upwardRequest = UpwardRequest(
        onRequestConsumed = {},
    )
)
