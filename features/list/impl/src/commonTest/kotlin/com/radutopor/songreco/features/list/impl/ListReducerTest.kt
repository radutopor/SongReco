package com.radutopor.songreco.features.list.impl

import com.radutopor.songreco.core.arch.stringvalue.StringValue
import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.list.impl.repository.ListModel
import com.radutopor.songreco.features.list.impl.testdoubles.listArgsSearchStub
import com.radutopor.songreco.features.list.impl.testdoubles.listArgsSimilarStub
import com.radutopor.songreco.features.list.impl.testdoubles.listStateRequestStub
import com.radutopor.songreco.features.list.impl.testdoubles.listStateStub
import com.radutopor.songreco.features.list.impl.testdoubles.repository.listModelErrorApiStub
import com.radutopor.songreco.features.list.impl.testdoubles.repository.listModelLoadedItemStub
import com.radutopor.songreco.features.list.impl.testdoubles.repository.listModelLoadedStub
import songreco.core.designsystem.generated.resources.msg_err_generic
import songreco.features.list.impl.generated.resources.Res
import songreco.features.list.impl.generated.resources.msg_err_no_results
import songreco.features.list.impl.generated.resources.title_search
import songreco.features.list.impl.generated.resources.title_similar
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import songreco.core.designsystem.generated.resources.Res as CoreRes

internal class ListReducerTest {

    private var actionSent: ListAction? = null

    private val reducer = ListReducer(
        actionSink = { actionSent = it },
    )

    @Test
    fun `GIVEN args Search WHEN initState retrieved THEN title is Search`() {
        val initState = reducer.initState(listArgsSearchStub)
        assertEquals(Res.string.title_search, initState.header.title)
    }

    @Test
    fun `GIVEN args Similar WHEN initState retrieved THEN title is Similar`() {
        val initState = reducer.initState(listArgsSimilarStub)
        assertEquals(Res.string.title_similar, initState.header.title)
    }

    @Test
    fun `WHEN initState retrieved THEN body is Loading`() {
        val initState = reducer.initState(listArgsSearchStub)
        assertEquals(ListState.Body.Loading, initState.body)
    }

    @Test
    fun `GIVEN initState WHEN onBackClick invoked THEN action BackClicked is sent`() {
        val initState = reducer.initState(listArgsSearchStub)
        initState.header.onBackClick()
        assertEquals(ListAction.BackClicked, actionSent)
    }

    @Test
    fun `GIVEN initState WHEN onUpwardRequest invoked THEN action UpwardRequestConsumed is sent`() {
        val initState = reducer.initState(listArgsSearchStub)
        initState.upwardRequest.onRequestConsumed()
        assertEquals(ListAction.UpwardRequestConsumed, actionSent)
    }

    @Test
    fun `GIVEN action ListModelAvailable AND ListModel Loaded WHEN reduce THEN body is Loaded mapped from ListModel`() {
        val listModelLoadedItem = listModelLoadedItemStub.copy(
            artist = "artist",
            name = "name",
        )
        val listModelLoaded = listModelLoadedStub.copy(
            items = listOf(listModelLoadedItem)
        )
        val action = ListAction.ListModelAvailable(listModelLoaded)
        val oldState = listStateStub.copy(body = ListState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        assertIs<ListState.Body.Loaded>(newState.body)
        assertEquals(listModelLoaded.items.size, newState.body.items.size)
        assertEquals(listModelLoadedItem.artist, newState.body.items.first().artist)
        assertEquals(listModelLoadedItem.name, newState.body.items.first().name)
    }

    @Test
    fun `GIVEN ListModel Loaded Item WHEN Item onClick invoked THEN action ListItemClicked is sent with Item model`() {
        val listModelLoadedItem = listModelLoadedItemStub
        val listModelLoaded = listModelLoadedStub.copy(
            items = listOf(listModelLoadedItem)
        )
        val action = ListAction.ListModelAvailable(listModelLoaded)
        val oldState = listStateStub.copy(body = ListState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        (newState.body as ListState.Body.Loaded).items.first().onClick()
        assertEquals(ListAction.ListItemClicked(listModelLoadedItem), actionSent)
    }

    @Test
    fun `GIVEN action ListModelAvailable AND ListModel Error NoResults WHEN reduce THEN body is Error AND message is no results`() {
        val listModelLoaded = ListModel.Error.NoResults
        val action = ListAction.ListModelAvailable(listModelLoaded)
        val oldState = listStateStub.copy(body = ListState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        assertIs<ListState.Body.Error>(newState.body)
        assertIs<StringValue.Res>(newState.body.message)
        assertEquals(Res.string.msg_err_no_results, newState.body.message.value)
        assertNull(newState.body.onRetryClick)
    }

    @Test
    fun `GIVEN action ListModelAvailable AND ListModel Error Api WHEN reduce THEN body is Error AND message is from api`() {
        val listModelLoaded = listModelErrorApiStub.copy(
            message = "message"
        )
        val action = ListAction.ListModelAvailable(listModelLoaded)
        val oldState = listStateStub.copy(body = ListState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        assertIs<ListState.Body.Error>(newState.body)
        assertIs<StringValue.Str>(newState.body.message)
        assertEquals(listModelLoaded.message, newState.body.message.value)
        assertNull(newState.body.onRetryClick)
    }

    @Test
    fun `GIVEN action ListModelAvailable AND ListModel Error Generic WHEN reduce THEN body is Error AND message is generic`() {
        val listModelLoaded = ListModel.Error.Generic
        val action = ListAction.ListModelAvailable(listModelLoaded)
        val oldState = listStateStub.copy(body = ListState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        assertIs<ListState.Body.Error>(newState.body)
        assertIs<StringValue.Res>(newState.body.message)
        assertEquals(CoreRes.string.msg_err_generic, newState.body.message.value)
        assertNotNull(newState.body.onRetryClick)
    }

    @Test
    fun `GIVEN Error with retry WHEN onRetryClick invoked THEN action RetryClicked is sent`() {
        val action = ListAction.ListModelAvailable(ListModel.Error.Generic)
        val oldState = listStateStub.copy(body = ListState.Body.Loading)
        val newState = reducer.run { oldState.reduce(action) }
        (newState.body as ListState.Body.Error).onRetryClick?.invoke()
        assertEquals(ListAction.RetryClicked, actionSent)
    }

    @Test
    fun `GIVEN action ListItemClicked WHEN reduce THEN upwardRequest is ShowDetailsContent with DetailsArgs`() {
        val listModelLoadedItem = listModelLoadedItemStub.copy(
            artist = "artist",
            name = "name",
        )
        val action = ListAction.ListItemClicked(listModelLoadedItem)
        val oldState = listStateStub
        val newState = reducer.run { oldState.reduce(action) }
        val expectedDetailsArgs = DetailsArgs(
            artist = listModelLoadedItem.artist,
            track = listModelLoadedItem.name,
        )
        assertEquals(ListState.Request.ShowDetailsContent(expectedDetailsArgs), newState.upwardRequest.request)
    }

    @Test
    fun `GIVEN action RetryClicked WHEN reduce THEN body is Loading`() {
        val action = ListAction.RetryClicked
        val oldState = listStateStub
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(ListState.Body.Loading, newState.body)
    }

    @Test
    fun `GIVEN action BackClicked WHEN reduce THEN upwardRequest is ShowPreviousContent`() {
        val action = ListAction.BackClicked
        val oldState = listStateStub
        val newState = reducer.run { oldState.reduce(action) }
        assertIs<ListState.Request.ShowPreviousContent>(newState.upwardRequest.request)
    }

    @Test
    fun `GIVEN action UpwardRequestConsumed WHEN reduce THEN upwardRequest is null`() {
        val action = ListAction.UpwardRequestConsumed
        val oldState = listStateStub.copy(
            upwardRequest = listStateStub.upwardRequest.copy(request = listStateRequestStub)
        )
        val newState = reducer.run { oldState.reduce(action) }
        assertNull(newState.upwardRequest.request)
    }
}