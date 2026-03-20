package com.radutopor.songreco.features.list.impl.testdoubles.repository

import com.radutopor.songreco.core.webapi.models.resources.TrackGetSimilarResource
import com.radutopor.songreco.core.webapi.models.resources.TrackSearchResource
import com.radutopor.songreco.core.webapi.models.responses.ErrorResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackGetSimilarResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackSearchResponse
import com.radutopor.songreco.core.webapi.testdoubles.trackGetSimilarResourceStub
import com.radutopor.songreco.core.webapi.testdoubles.trackSearchResourceStub
import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.list.impl.repository.ListModel
import com.radutopor.songreco.features.list.impl.repository.ListWebApiMapper

internal class ListWebApiMapperFake : ListWebApiMapper {

    var getTrackSearchResourceReturn: (ListArgs.Search) -> TrackSearchResource = { trackSearchResourceStub }
    override fun getTrackSearchResource(args: ListArgs.Search) = getTrackSearchResourceReturn(args)

    var getTrackSimilarResourceReturn: (ListArgs.Similar) -> TrackGetSimilarResource = { trackGetSimilarResourceStub }
    override fun getTrackSimilarResource(args: ListArgs.Similar) = getTrackSimilarResourceReturn(args)

    var searchGetListModelLoadedReturn: (TrackSearchResponse) -> ListModel.Loaded = { listModelLoadedStub }
    override fun getListModelLoaded(response: TrackSearchResponse) = searchGetListModelLoadedReturn(response)

    var similarGetListModelLoadedReturn: (TrackGetSimilarResponse) -> ListModel.Loaded = { listModelLoadedStub }
    override fun getListModelLoaded(response: TrackGetSimilarResponse) = similarGetListModelLoadedReturn(response)

    var getListModelErrorApiReturn: (ErrorResponse) -> ListModel.Error.Api = { listModelErrorApiStub }
    override fun getListModelErrorApi(response: ErrorResponse) = getListModelErrorApiReturn(response)
}
