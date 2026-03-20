package com.radutopor.songreco.features.list.impl.repository

import com.radutopor.songreco.core.webapi.models.resources.TrackGetSimilarResource
import com.radutopor.songreco.core.webapi.models.resources.TrackSearchResource
import com.radutopor.songreco.core.webapi.models.responses.ErrorResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackGetSimilarResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackSearchResponse
import com.radutopor.songreco.features.list.api.ListArgs

internal interface ListWebApiMapper {
    fun getTrackSearchResource(args: ListArgs.Search): TrackSearchResource
    fun getTrackSimilarResource(args: ListArgs.Similar): TrackGetSimilarResource
    fun getListModelLoaded(response: TrackSearchResponse): ListModel.Loaded
    fun getListModelLoaded(response: TrackGetSimilarResponse): ListModel.Loaded
    fun getListModelErrorApi(response: ErrorResponse): ListModel.Error.Api
}

internal class ListWebApiMapperImpl : ListWebApiMapper {

    //region from Args to Resources

    override fun getTrackSearchResource(args: ListArgs.Search): TrackSearchResource =
        TrackSearchResource(
            track = args.track,
        )

    override fun getTrackSimilarResource(args: ListArgs.Similar): TrackGetSimilarResource =
        TrackGetSimilarResource(
            artist = args.artist,
            track = args.track,
        )

    // endregion

    //region from Responses to Model

    override fun getListModelLoaded(response: TrackSearchResponse): ListModel.Loaded =
        ListModel.Loaded(
            items = response.results.trackMatches.track.map { searchResult ->
                ListModel.Loaded.Item(
                    artist = searchResult.artist,
                    name = searchResult.name,
                )
            }
        )

    override fun getListModelLoaded(response: TrackGetSimilarResponse): ListModel.Loaded =
        ListModel.Loaded(
            items = response.similarTracks.track.map { similarTrack ->
                ListModel.Loaded.Item(
                    artist = similarTrack.artist.name,
                    name = similarTrack.name,
                )
            }
        )

    override fun getListModelErrorApi(response: ErrorResponse): ListModel.Error.Api =
        ListModel.Error.Api(
            message = response.message,
        )

    // endregion
}
