package com.radutopor.songreco.features.list.impl

import com.radutopor.songreco.core.arch.stringvalue.StringValue
import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.details.api.DetailsArgs
import org.jetbrains.compose.resources.StringResource

data class ListState(
    val header: Header,
    val body: Body,
    val upwardRequest: UpwardRequest<Request>,
) {

    data class Header(
        val title: StringResource,
        val onBackClick: () -> Unit,
    )

    sealed class Body {
        object Loading : Body()

        data class Loaded(
            val items: List<Item>,
        ) : Body() {

            data class Item(
                val artist: String,
                val name: String,
                val onClick: () -> Unit,
            )
        }

        data class Error(
            val message: StringValue,
            val onRetryClick: (() -> Unit)?,
        ) : Body()
    }

    sealed class Request {
        data class ShowDetailsContent(val args: DetailsArgs) : Request()
        object ShowPreviousContent : Request()
    }
}