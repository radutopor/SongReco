package com.radutopor.songreco.features.search.impl

internal sealed class SearchAction {

    data class SearchTextChanged(val text: String) : SearchAction()

    object SearchButtonClicked : SearchAction()

    object UpwardRequestConsumed : SearchAction()
}