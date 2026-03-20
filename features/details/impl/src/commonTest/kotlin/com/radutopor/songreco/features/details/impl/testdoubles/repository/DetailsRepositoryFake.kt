package com.radutopor.songreco.features.details.impl.testdoubles.repository

import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.details.impl.repository.DetailsModel
import com.radutopor.songreco.features.details.impl.repository.DetailsRepository

internal class DetailsRepositoryFake : DetailsRepository {

    var getDetailsModelArg: DetailsArgs? = null
    var getDetailsModelReturn: (DetailsArgs) -> DetailsModel = {
        getDetailsModelArg = it
        detailsModelLoadedStub
    }

    override suspend fun getDetailsModel(args: DetailsArgs): DetailsModel = getDetailsModelReturn(args)
}
