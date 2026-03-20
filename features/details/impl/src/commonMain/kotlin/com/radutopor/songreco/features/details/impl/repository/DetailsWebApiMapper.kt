package com.radutopor.songreco.features.details.impl.repository

import com.radutopor.songreco.core.webapi.models.resources.TrackGetInfoResource
import com.radutopor.songreco.core.webapi.models.responses.ErrorResponse
import com.radutopor.songreco.core.webapi.models.responses.TrackGetInfoResponse
import com.radutopor.songreco.features.details.api.DetailsArgs

internal interface DetailsWebApiMapper {
    fun getTrackInfoResource(args: DetailsArgs): TrackGetInfoResource
    fun getDetailsModelLoaded(response: TrackGetInfoResponse): DetailsModel.Loaded
    fun getDetailsModelErrorApi(response: ErrorResponse): DetailsModel.Error.Api
}

internal class DetailsWebApiMapperImpl : DetailsWebApiMapper {

    //region from Args to Resources

    override fun getTrackInfoResource(args: DetailsArgs): TrackGetInfoResource =
        TrackGetInfoResource(
            artist = args.artist,
            track = args.track,
        )

    // endregion

    //region from Responses to Model

    override fun getDetailsModelLoaded(response: TrackGetInfoResponse): DetailsModel.Loaded =
        DetailsModel.Loaded(
            artist = response.track.artist.name,
            name = response.track.name,
            duration = getMinutesAndSeconds(response.track.duration),
            albumArtUrl = response.track.album.image.last().text,
            albumName = response.track.album.title,
        )

    private fun getMinutesAndSeconds(millis: Long): String {
        val totalSeconds = millis.div(1000)
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "${minutes}:${seconds.toString().padStart(2, '0')}"
    }

    override fun getDetailsModelErrorApi(response: ErrorResponse): DetailsModel.Error.Api =
        DetailsModel.Error.Api(
            message = response.message
        )

    // endregion
}
