package com.radutopor.songreco.features.details.impl.testdoubles.repository

import com.radutopor.songreco.features.details.impl.repository.DetailsModel

internal val detailsModelLoadedStub = DetailsModel.Loaded(
    artist = "",
    name = "",
    duration = "",
    albumArtUrl = "",
    albumName = "",
)

internal val detailsModelErrorApiStub = DetailsModel.Error.Api(
    message = "",
)

internal val detailsModelErrorGenericStub = DetailsModel.Error.Generic
