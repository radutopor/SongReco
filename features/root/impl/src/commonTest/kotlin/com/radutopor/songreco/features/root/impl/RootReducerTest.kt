package com.radutopor.songreco.features.root.impl

import com.radutopor.songreco.core.arch.substatestack.SubstateStack
import com.radutopor.songreco.core.arch.substatestack.SubstateStackItem
import com.radutopor.songreco.core.designsystem.elements.Colors
import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.details.impl.DetailsState
import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.list.impl.ListState
import com.radutopor.songreco.features.root.impl.testdoubles.SubstateProviderFake
import com.radutopor.songreco.features.root.impl.testdoubles.detailsContentStub
import com.radutopor.songreco.features.root.impl.testdoubles.detailsStateStub
import com.radutopor.songreco.features.root.impl.testdoubles.listContentStub
import com.radutopor.songreco.features.root.impl.testdoubles.listStateStub
import com.radutopor.songreco.features.root.impl.testdoubles.rootStateStub
import com.radutopor.songreco.features.root.impl.testdoubles.searchContentStub
import com.radutopor.songreco.features.root.impl.testdoubles.searchStateStub
import com.radutopor.songreco.features.root.impl.testdoubles.trendingContentStub
import com.radutopor.songreco.features.search.impl.SearchState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestScope
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class RootReducerTest {

    private val substateProvider = SubstateProviderFake()
    private val featureScope = TestScope()
    private var actionSent: RootAction? = null

    private val reducer = RootReducer(
        substateProvider = substateProvider,
        featureScope = featureScope,
        actionSink = { actionSent = it },
    )

    @Test
    fun `WHEN initState retrieved THEN content is Search`() {
        val initState = reducer.initState
        assertIs<RootState.Content.Search>(initState.contentStack.current)
    }

    @Test
    fun `WHEN initState retrieved THEN backNavInterceptor is null`() {
        val initState = reducer.initState
        assertNull(initState.backNavInterceptor)
    }

    @Test
    fun `WHEN initState retrieved THEN Search tab exclusively has selected colors`() {
        val initState = reducer.initState
        assertEquals(Colors.SecondaryDisabled, initState.tabBar.search.bgColor)
        assertEquals(Colors.OnPrimary, initState.tabBar.search.contentColor)
        assertEquals(Colors.Secondary, initState.tabBar.trending.bgColor)
        assertEquals(Colors.Tertiary, initState.tabBar.trending.contentColor)
    }

    @Test
    fun `GIVEN initState WHEN search onClick invoked THEN action SearchTabClicked is sent`() {
        val initState = reducer.initState
        initState.tabBar.search.onClick()
        assertEquals(RootAction.SearchTabClicked, actionSent)
    }

    @Test
    fun `GIVEN initState WHEN trending onClick invoked THEN action TrendingTabClicked is sent`() {
        val initState = reducer.initState
        initState.tabBar.trending.onClick()
        assertEquals(RootAction.TrendingTabClicked, actionSent)
    }

    @Test
    fun `GIVEN action NewContentAvailable WHEN reduce THEN content is replaced`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(searchContentStub),
        )
        val newContent = searchContentStub.copy(
            state = searchStateStub.copy(
                searchBox = searchStateStub.searchBox.copy(
                    text = "New text"
                )
            )
        )
        val action = RootAction.NewContentAvailable(newContent)
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(newContent, newState.contentStack.current)
    }

    @Test
    fun `GIVEN action NewContentAvailable AND content request Search ShowListContent WHEN reduce THEN List substate is added to contentStack`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(searchContentStub),
        )
        val newContent = searchContentStub.copy(
            state = searchStateStub.copy(
                upwardRequest = searchStateStub.upwardRequest.copy(
                    request = SearchState.Request.ShowListContent(ListArgs.Search(""))
                )
            )
        )
        val action = RootAction.NewContentAvailable(newContent)
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(2, newState.contentStack.backstack.size)
        assertIs<RootState.Content.Search>(newState.contentStack.backstack[0].observableState.value)
        assertIs<RootState.Content.List>(newState.contentStack.current)
    }

    @Test
    fun `GIVEN action NewContentAvailable AND content request List ShowDetailsContent WHEN reduce THEN Details substate is added to contentStack`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(listContentStub),
        )
        val newContent = listContentStub.copy(
            state = listStateStub.copy(
                upwardRequest = listStateStub.upwardRequest.copy(
                    request = ListState.Request.ShowDetailsContent(DetailsArgs("", ""))
                )
            )
        )
        val action = RootAction.NewContentAvailable(newContent)
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(2, newState.contentStack.backstack.size)
        assertIs<RootState.Content.List>(newState.contentStack.backstack[0].observableState.value)
        assertIs<RootState.Content.Details>(newState.contentStack.current)
    }

    @Test
    fun `GIVEN action NewContentAvailable AND content request List ShowPreviousContent WHEN reduce THEN List substate is removed from contentStack`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(searchContentStub, listContentStub),
        )
        val newContent = listContentStub.copy(
            state = listStateStub.copy(
                upwardRequest = listStateStub.upwardRequest.copy(
                    request = ListState.Request.ShowPreviousContent
                )
            )
        )
        val action = RootAction.NewContentAvailable(newContent)
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(1, newState.contentStack.backstack.size)
        assertIs<RootState.Content.Search>(newState.contentStack.current)
    }

    @Test
    fun `GIVEN action NewContentAvailable AND content request Details ShowListContent WHEN reduce THEN List substate is added to contentStack`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(detailsContentStub),
        )
        val newContent = detailsContentStub.copy(
            state = detailsStateStub.copy(
                upwardRequest = detailsStateStub.upwardRequest.copy(
                    request = DetailsState.Request.ShowListContent(ListArgs.Similar("", ""))
                )
            )
        )
        val action = RootAction.NewContentAvailable(newContent)
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(2, newState.contentStack.backstack.size)
        assertIs<RootState.Content.Details>(newState.contentStack.backstack[0].observableState.value)
        assertIs<RootState.Content.List>(newState.contentStack.current)
    }

    @Test
    fun `GIVEN action NewContentAvailable AND content request Details ShowPreviousContent WHEN reduce THEN Details substate is removed from contentStack`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(listContentStub, detailsContentStub),
        )
        val newContent = detailsContentStub.copy(
            state = detailsStateStub.copy(
                upwardRequest = detailsStateStub.upwardRequest.copy(
                    request = DetailsState.Request.ShowPreviousContent
                )
            )
        )
        val action = RootAction.NewContentAvailable(newContent)
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(1, newState.contentStack.backstack.size)
        assertIs<RootState.Content.List>(newState.contentStack.current)
    }

    @Test
    fun `GIVEN action adding a stack item WHEN reduce THEN backNavInterceptor is set`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(searchContentStub),
            backNavInterceptor = null,
        )
        val newContent = searchContentStub.copy(
            state = searchStateStub.copy(
                upwardRequest = searchStateStub.upwardRequest.copy(
                    request = SearchState.Request.ShowListContent(ListArgs.Search(""))
                )
            )
        )
        val action = RootAction.NewContentAvailable(newContent)
        val newState = reducer.run { oldState.reduce(action) }
        assertNotNull(newState.backNavInterceptor)
    }

    @Test
    fun `GIVEN action removing a second stack item WHEN reduce THEN backNavInterceptor is unset`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(searchContentStub, listContentStub),
            backNavInterceptor = { },
        )
        val newContent = listContentStub.copy(
            state = listStateStub.copy(
                upwardRequest = listStateStub.upwardRequest.copy(
                    request = ListState.Request.ShowPreviousContent
                )
            )
        )
        val action = RootAction.NewContentAvailable(newContent)
        val newState = reducer.run { oldState.reduce(action) }
        assertNull(newState.backNavInterceptor)
    }

    @Test
    fun `GIVEN backNavInterceptor is set WHEN backNavInterceptor invoked THEN action BackNavIntercepted is sent`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(searchContentStub),
        )
        val newContent = searchContentStub.copy(
            state = searchStateStub.copy(
                upwardRequest = searchStateStub.upwardRequest.copy(
                    request = SearchState.Request.ShowListContent(ListArgs.Search(""))
                )
            )
        )
        val action = RootAction.NewContentAvailable(newContent)
        val newState = reducer.run { oldState.reduce(action) }
        newState.backNavInterceptor?.invoke()
        assertEquals(RootAction.BackNavIntercepted, actionSent)
    }

    @Test
    fun `GIVEN action SearchTabClicked WHEN reduce THEN Search substate is replaced in contentStack`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(trendingContentStub),
        )
        val action = RootAction.SearchTabClicked
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(1, newState.contentStack.backstack.size)
        assertIs<RootState.Content.Search>(newState.contentStack.current)
    }

    @Test
    fun `GIVEN action SearchTabClicked WHEN reduce THEN Search tab exclusively has selected colors`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(trendingContentStub),
        )
        val action = RootAction.SearchTabClicked
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(Colors.SecondaryDisabled, newState.tabBar.search.bgColor)
        assertEquals(Colors.OnPrimary, newState.tabBar.search.contentColor)
        assertEquals(Colors.Secondary, newState.tabBar.trending.bgColor)
        assertEquals(Colors.Tertiary, newState.tabBar.trending.contentColor)
    }

    @Test
    fun `GIVEN action TrendingTabClicked WHEN reduce THEN Trending substate is replaced in contentStack`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(searchContentStub),
        )
        val action = RootAction.TrendingTabClicked
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(1, newState.contentStack.backstack.size)
        assertIs<RootState.Content.Trending>(newState.contentStack.current)
    }

    @Test
    fun `GIVEN action TrendingTabClicked WHEN reduce THEN Trending tab exclusively has selected colors`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(searchContentStub),
        )
        val action = RootAction.TrendingTabClicked
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(Colors.SecondaryDisabled, newState.tabBar.trending.bgColor)
        assertEquals(Colors.OnPrimary, newState.tabBar.trending.contentColor)
        assertEquals(Colors.Secondary, newState.tabBar.search.bgColor)
        assertEquals(Colors.Tertiary, newState.tabBar.search.contentColor)
    }

    @Test
    fun `GIVEN action BackNavIntercepted WHEN reduce THEN top substate is removed from contentStack`() {
        val oldState = rootStateStub.copy(
            contentStack = rootStateContentStackStub(searchContentStub, listContentStub),
            backNavInterceptor = { },
        )
        val action = RootAction.BackNavIntercepted
        val newState = reducer.run { oldState.reduce(action) }
        assertEquals(1, newState.contentStack.backstack.size)
        assertIs<RootState.Content.Search>(newState.contentStack.current)
    }

    private fun rootStateContentStackStub(vararg contentStub: RootState.Content) =
        SubstateStack(
            current = contentStub.last(),
            backstack = contentStub.map {
                SubstateStackItem(
                    observableState = MutableStateFlow(it),
                    observationScope = featureScope,
                )
            },
        )

    @AfterTest
    fun tearDown() {
        featureScope.cancel()
    }
}