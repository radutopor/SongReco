package com.radutopor.songreco.features.list.impl.repository

internal sealed class ListModel {

    data class Loaded(
        val items: List<Item>,
    ) : ListModel() {

        data class Item(
            val artist: String,
            val name: String,
        )
    }

    sealed class Error(
        val isRetriable: Boolean = false,
    ) : ListModel() {

        object NoResults : Error()

        data class Api(
            val message: String,
        ) : Error()

        object Generic : Error(isRetriable = true)
    }
}