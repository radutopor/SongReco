package com.radutopor.songreco.features.search.impl

import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.list.api.ListArgs

data class SearchState(
    val searchBox: SearchBox,
    val searchButton: SearchButton,
    val upwardRequest: UpwardRequest<Request>,
) {

    data class SearchBox(
        val text: String,
        val onTextChange: (String) -> Unit,
    )

    data class SearchButton(
        val enabled: Boolean,
        val onClick: () -> Unit,
    )

    sealed class Request {
        data class ShowListContent(val args: ListArgs.Search) : Request()
    }
}