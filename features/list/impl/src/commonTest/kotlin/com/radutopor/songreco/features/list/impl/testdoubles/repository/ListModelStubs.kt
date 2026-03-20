package com.radutopor.songreco.features.list.impl.testdoubles.repository

import com.radutopor.songreco.features.list.impl.repository.ListModel

internal val listModelLoadedItemStub = ListModel.Loaded.Item(
    artist = "",
    name = "",
)

internal val listModelLoadedStub = ListModel.Loaded(
    items = listOf(listModelLoadedItemStub),
)

internal val listModelErrorApiStub = ListModel.Error.Api(
    message = "",
)
