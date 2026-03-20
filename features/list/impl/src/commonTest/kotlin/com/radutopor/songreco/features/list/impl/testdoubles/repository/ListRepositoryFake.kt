package com.radutopor.songreco.features.list.impl.testdoubles.repository

import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.list.impl.repository.ListModel
import com.radutopor.songreco.features.list.impl.repository.ListRepository

internal class ListRepositoryFake : ListRepository {

    var getListModelArg: ListArgs? = null
    var getListModelReturn: (ListArgs) -> ListModel = {
        getListModelArg = it
        listModelLoadedStub
    }

    override suspend fun getListModel(args: ListArgs): ListModel = getListModelReturn(args)
}
