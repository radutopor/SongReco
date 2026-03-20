package com.radutopor.songreco.features.list.api

sealed class ListArgs {

    data class Search(
        val track: String,
    ) : ListArgs()

    data class Similar(
        val artist: String,
        val track: String,
    ) : ListArgs()
}
