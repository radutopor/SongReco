package com.radutopor.songreco.features.details.impl.testdoubles.repository

import com.radutopor.songreco.core.webapi.models.resources.TrackGetInfoResource
import com.radutopor.songreco.core.webapi.models.responses.ErrorResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackGetInfoResponse
import com.radutopor.songreco.core.webapi.testdoubles.trackGetInfoResourceStub
import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.details.impl.repository.DetailsModel
import com.radutopor.songreco.features.details.impl.repository.DetailsWebApiMapper

internal class DetailsWebApiMapperFake : DetailsWebApiMapper {

    var getTrackInfoResourceReturn: (DetailsArgs) -> TrackGetInfoResource = { trackGetInfoResourceStub }
    override fun getTrackInfoResource(args: DetailsArgs): TrackGetInfoResource = getTrackInfoResourceReturn(args)

    var getDetailsModelLoadedReturn: (TrackGetInfoResponse) -> DetailsModel.Loaded = { detailsModelLoadedStub }
    override fun getDetailsModelLoaded(response: TrackGetInfoResponse): DetailsModel.Loaded = getDetailsModelLoadedReturn(response)

    var getDetailsModelErrorApiReturn: (ErrorResponse) -> DetailsModel.Error.Api = { detailsModelErrorApiStub }
    override fun getDetailsModelErrorApi(response: ErrorResponse): DetailsModel.Error.Api = getDetailsModelErrorApiReturn(response)
}
