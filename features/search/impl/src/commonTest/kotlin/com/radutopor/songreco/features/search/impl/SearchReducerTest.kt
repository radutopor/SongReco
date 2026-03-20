package com.radutopor.songreco.features.search.impl

import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.search.impl.testdoubles.searchStateRequestStub
import com.radutopor.songreco.features.search.impl.testdoubles.searchStateSearchBoxStub
import com.radutopor.songreco.features.search.impl.testdoubles.searchStateSearchButtonStub
import com.radutopor.songreco.features.search.impl.testdoubles.searchStateStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class SearchReducerTest {

    private var actionSent: SearchAction? = null

    private val reducer = SearchReducer(
        actionSink = { actionSent = it },
    )

    @Test
    fun `WHEN initState retrieved THEN searchBox text is empty`() {
        val initState = reducer.initState
        assertEquals("", initState.searchBox.text)
    }

    @Test
    fun `WHEN initState retrieved THEN searchButton is disabled`() {
        val initState = reducer.initState
        assertFalse(initState.searchButton.enabled)
    }

    @Test
    fun `GIVEN initState WHEN onTextChange invoked THEN action SearchTextChanged is sent`() {
        val initState = reducer.initState
        val text = "new text"
        initState.searchBox.onTextChange(text)
        assertEquals(SearchAction.SearchTextChanged(text), actionSent)
    }

    @Test
    fun `GIVEN initState WHEN onClick invoked THEN action SearchButtonClicked is sent`() {
        val initState = reducer.initState
        initState.searchButton.onClick()
        assertEquals(SearchAction.SearchButtonClicked, actionSent)
    }

    @Test
    fun `GIVEN initState WHEN onRequestConsumed invoked THEN action UpwardRequestConsumed is sent`() {
        val initState = reducer.initState
        initState.upwardRequest.onRequestConsumed()
        assertEquals(SearchAction.UpwardRequestConsumed, actionSent)
    }

    @Test
    fun `GIVEN action SearchTextChanged WHEN reduce THEN searchBox text is updated AND searchButton enabled status is updated`() {
        val oldState = searchStateStub.copy(
            searchBox = searchStateSearchBoxStub.copy(text = ""),
            searchButton = searchStateSearchButtonStub.copy(enabled = false)
        )
        val text = "search query"
        val action = SearchAction.SearchTextChanged(text)
        val newState = reducer.run { oldState.reduce(action) }

        assertEquals(text, newState.searchBox.text)
        assertTrue(newState.searchButton.enabled)
    }

    @Test
    fun `GIVEN action SearchTextChanged with blank text WHEN reduce THEN searchButton is disabled`() {
        val oldState = searchStateStub.copy(
            searchBox = searchStateSearchBoxStub.copy(text = "previous"),
            searchButton = searchStateSearchButtonStub.copy(enabled = true)
        )
        val action = SearchAction.SearchTextChanged(" ")
        val newState = reducer.run { oldState.reduce(action) }

        assertFalse(newState.searchButton.enabled)
    }

    @Test
    fun `GIVEN action SearchButtonClicked WHEN reduce THEN upwardRequest is ShowListContent with Search args`() {
        val text = "query"
        val oldState = searchStateStub.copy(
            searchBox = searchStateSearchBoxStub.copy(text = text)
        )
        val action = SearchAction.SearchButtonClicked
        val newState = reducer.run { oldState.reduce(action) }

        val expectedArgs = ListArgs.Search(track = text)
        assertEquals(SearchState.Request.ShowListContent(expectedArgs), newState.upwardRequest.request)
    }

    @Test
    fun `GIVEN action UpwardRequestConsumed WHEN reduce THEN upwardRequest is null`() {
        val oldState = searchStateStub.copy(
            upwardRequest = searchStateStub.upwardRequest.copy(request = searchStateRequestStub)
        )
        val action = SearchAction.UpwardRequestConsumed
        val newState = reducer.run { oldState.reduce(action) }

        assertNull(newState.upwardRequest.request)
    }
}
