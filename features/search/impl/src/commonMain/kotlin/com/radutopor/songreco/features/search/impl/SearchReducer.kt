package com.radutopor.songreco.features.search.impl

import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.list.api.ListArgs

internal class SearchReducerFactory {
    fun get(actionSink: (SearchAction) -> Unit) = SearchReducer(actionSink)
}

internal class SearchReducer(
    private val actionSink: (SearchAction) -> Unit,
) {

    val initState = SearchState(
        searchBox = SearchState.SearchBox(
            text = "",
            onTextChange = { actionSink(SearchAction.SearchTextChanged(it)) },
        ),
        searchButton = SearchState.SearchButton(
            enabled = false,
            onClick = { actionSink(SearchAction.SearchButtonClicked) },
        ),
        upwardRequest = UpwardRequest(
            onRequestConsumed = { actionSink(SearchAction.UpwardRequestConsumed) },
        ),
    )

    fun SearchState.reduce(action: SearchAction): SearchState =
        when (action) {
            is SearchAction.SearchTextChanged -> withSearchBoxText(action.text)
            is SearchAction.SearchButtonClicked -> withShowListSearchRequest()
            is SearchAction.UpwardRequestConsumed -> withClearedRequest()
        }

    private fun SearchState.withSearchBoxText(text: String): SearchState =
        copy(
            searchBox = searchBox.copy(text = text),
            searchButton = searchButton.copy(enabled = text.isNotBlank()),
        )

    private fun SearchState.withShowListSearchRequest(): SearchState {
        val listArgs = ListArgs.Search(track = searchBox.text)
        val request = SearchState.Request.ShowListContent(listArgs)
        return withUpwardRequest(request)
    }

    private fun SearchState.withClearedRequest(): SearchState =
        withUpwardRequest(null)

    private fun SearchState.withUpwardRequest(request: SearchState.Request?): SearchState =
        copy(upwardRequest = upwardRequest.copy(request = request))
}
