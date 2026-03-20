package com.radutopor.songreco.features.details.impl

import com.radutopor.songreco.core.arch.stringvalue.StringValue
import com.radutopor.songreco.features.details.impl.repository.DetailsModel
import com.radutopor.songreco.features.details.impl.testdoubles.detailsArgsStub
import com.radutopor.songreco.features.details.impl.testdoubles.detailsStateRequestStub
import com.radutopor.songreco.features.details.impl.testdoubles.detailsStateStub
import com.radutopor.songreco.features.details.impl.testdoubles.repository.detailsModelErrorApiStub
import com.radutopor.songreco.features.details.impl.testdoubles.repository.detailsModelLoadedStub
import com.radutopor.songreco.features.list.api.ListArgs
import songreco.core.designsystem.generated.resources.msg_err_generic
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import songreco.core.designsystem.generated.resources.Res as CoreRes

internal class DetailsReducerTest {

    private val detailsArgs = detailsArgsStub.copy(
        artist = "artist",
        track = "track",
    )
    private var actionSent: DetailsAction? = null

    private val reducer = DetailsReducer(
        args = detailsArgs,
        actionSink = { actionSent = it },
    )

    @Test
    fun `WHEN initState retrieved THEN body is Loading`() {
        val initState = reducer.initState
        assertEquals(DetailsState.Body.Loading, initState.body)
    }

    @Test
    fun `GIVEN initState WHEN onBackClick invoked THEN action BackClicked is sent`() {
        val initState = reducer.initState
        initState.onBackClick()
        assertEquals(DetailsAction.BackClicked, actionSent)
    }

    @Test
    fun `GIVEN initState WHEN onUpwardRequest invoked THEN action UpwardRequestConsumed is sent`() {
        val initState = reducer.initState
        initState.upwardRequest.onRequestConsumed()
        assertEquals(DetailsAction.UpwardRequestConsumed, actionSent)
    }

    @Test
    fun `GIVEN action DetailsModelAvailable AND DetailsModel Loaded WHEN reduce THEN body is Loaded mapped from DetailsModel`() {
        val detailsModelLoaded = detailsModelLoadedStub.copy(
            artist = "artist",
            name = "name",
            duration = "3:05",
            albumArtUrl = "url",
            albumName = "album",
        )
        val action = DetailsAction.DetailsModelAvailable(detailsModelLoaded)
        val oldState = detailsStateStub.copy(body = DetailsState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        assertIs<DetailsState.Body.Loaded>(newState.body)
        assertEquals(detailsModelLoaded.artist, newState.body.artist)
        assertEquals(detailsModelLoaded.name, newState.body.name)
        assertEquals(detailsModelLoaded.duration, newState.body.duration)
        assertEquals(detailsModelLoaded.albumArtUrl, newState.body.albumArtUrl)
        assertEquals(detailsModelLoaded.albumName, newState.body.albumName)
    }

    @Test
    fun `GIVEN DetailsModel Loaded WHEN onSimilarClick invoked THEN action SimilarClicked is sent`() {
        val action = DetailsAction.DetailsModelAvailable(detailsModelLoadedStub)
        val oldState = detailsStateStub.copy(body = DetailsState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        (newState.body as DetailsState.Body.Loaded).onSimilarClick()
        assertEquals(DetailsAction.SimilarClicked, actionSent)
    }

    @Test
    fun `GIVEN action DetailsModelAvailable AND DetailsModel Error Api WHEN reduce THEN body is Error AND message is from api`() {
        val detailsModelError = detailsModelErrorApiStub.copy(
            message = "message"
        )
        val action = DetailsAction.DetailsModelAvailable(detailsModelError)
        val oldState = detailsStateStub.copy(body = DetailsState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        assertIs<DetailsState.Body.Error>(newState.body)
        assertIs<StringValue.Str>(newState.body.message)
        assertEquals(detailsModelError.message, newState.body.message.value)
        assertNull(newState.body.onRetryClick)
    }

    @Test
    fun `GIVEN action DetailsModelAvailable AND DetailsModel Error Generic WHEN reduce THEN body is Error AND message is generic`() {
        val detailsModelError = DetailsModel.Error.Generic
        val action = DetailsAction.DetailsModelAvailable(detailsModelError)
        val oldState = detailsStateStub.copy(body = DetailsState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        assertIs<DetailsState.Body.Error>(newState.body)
        assertIs<StringValue.Res>(newState.body.message)
        assertEquals(CoreRes.string.msg_err_generic, newState.body.message.value)
        assertNotNull(newState.body.onRetryClick)
    }

    @Test
    fun `GIVEN Error with retry WHEN onRetryClick invoked THEN action RetryClicked is sent`() {
        val action = DetailsAction.DetailsModelAvailable(DetailsModel.Error.Generic)
        val oldState = detailsStateStub.copy(body = DetailsState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        (newState.body as DetailsState.Body.Error).onRetryClick?.invoke()
        assertEquals(DetailsAction.RetryClicked, actionSent)
    }

    @Test
    fun `GIVEN action SimilarClicked WHEN reduce THEN upwardRequest is ShowListContent with Similar args`() {
        val action = DetailsAction.SimilarClicked
        val oldState = detailsStateStub
        val newState = reducer.run { oldState.reduce(action) }
        val expectedListArgs = ListArgs.Similar(
            artist = detailsArgs.artist,
            track = detailsArgs.track,
        )
        assertEquals(DetailsState.Request.ShowListContent(expectedListArgs), newState.upwardRequest.request)
    }

    @Test
    fun `GIVEN action RetryClicked WHEN reduce THEN body is Loading`() {
        val action = DetailsAction.RetryClicked
        val oldState = detailsStateStub
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(DetailsState.Body.Loading, newState.body)
    }

    @Test
    fun `GIVEN action BackClicked WHEN reduce THEN upwardRequest is ShowPreviousContent`() {
        val action = DetailsAction.BackClicked
        val oldState = detailsStateStub
        val newState = reducer.run { oldState.reduce(action) }
        assertIs<DetailsState.Request.ShowPreviousContent>(newState.upwardRequest.request)
    }

    @Test
    fun `GIVEN action UpwardRequestConsumed WHEN reduce THEN upwardRequest is null`() {
        val action = DetailsAction.UpwardRequestConsumed
        val oldState = detailsStateStub.copy(
            upwardRequest = detailsStateStub.upwardRequest.copy(request = detailsStateRequestStub)
        )
        val newState = reducer.run { oldState.reduce(action) }
        assertNull(newState.upwardRequest.request)
    }
}
