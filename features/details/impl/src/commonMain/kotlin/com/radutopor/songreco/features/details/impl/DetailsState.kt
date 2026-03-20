package com.radutopor.songreco.features.details.impl

import com.radutopor.songreco.core.arch.stringvalue.StringValue
import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.list.api.ListArgs

data class DetailsState(
    val onBackClick: () -> Unit,
    val body: Body,
    val upwardRequest: UpwardRequest<Request>,
) {

    sealed class Body {
        object Loading : Body()

        data class Loaded(
            val albumArtUrl: String,
            val name: String,
            val artist: String,
            val albumName: String,
            val duration: String,
            val onSimilarClick: () -> Unit,
        ) : Body()

        data class Error(
            val message: StringValue,
            val onRetryClick: (() -> Unit)?,
        ) : Body()
    }

    sealed class Request {
        data class ShowListContent(val args: ListArgs.Similar) : Request()
        object ShowPreviousContent : Request()
    }
}