package com.radutopor.songreco.features.list.impl

import com.radutopor.songreco.features.list.impl.repository.ListModel

internal sealed class ListAction {

    data class ListModelAvailable(val model: ListModel) : ListAction()

    data class ListItemClicked(val item: ListModel.Loaded.Item) : ListAction()

    object RetryClicked : ListAction()

    object BackClicked : ListAction()

    object UpwardRequestConsumed : ListAction()
}