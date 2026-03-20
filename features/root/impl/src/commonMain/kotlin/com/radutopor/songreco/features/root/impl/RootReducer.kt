package com.radutopor.songreco.features.root.impl

import androidx.compose.ui.graphics.Color.Companion.Unspecified
import com.radutopor.songreco.core.arch.substatestack.SubstateStack
import com.radutopor.songreco.core.arch.substatestack.SubstateStackItem
import com.radutopor.songreco.core.arch.substatestack.pop
import com.radutopor.songreco.core.arch.substatestack.push
import com.radutopor.songreco.core.arch.substatestack.replace
import com.radutopor.songreco.core.arch.substatestack.toSubstateStack
import com.radutopor.songreco.core.arch.upwardrequest.consume
import com.radutopor.songreco.core.arch.utils.map
import com.radutopor.songreco.core.designsystem.elements.Colors
import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.details.impl.DetailsState
import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.list.impl.ListState
import com.radutopor.songreco.features.search.impl.SearchState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

internal class RootReducerFactory(
    private val substateProvider: SubstateProvider,
) {
    fun get(
        featureScope: CoroutineScope,
        actionSink: (RootAction) -> Unit
    ) = RootReducer(substateProvider, featureScope, actionSink)
}

internal class RootReducer(
    private val substateProvider: SubstateProvider,
    private val featureScope: CoroutineScope,
    private val actionSink: (RootAction) -> Unit,
) {

    private val tabStub = RootState.TabBar.Tab(Unspecified, Unspecified) { }

    val initState = RootState(
        contentStack = getSearchStackItem().toSubstateStack(::onNewSubstate),
        tabBar = RootState.TabBar(
            search = tabStub.withSelection(true).copy(
                onClick = { actionSink(RootAction.SearchTabClicked) },
            ),
            trending = tabStub.withSelection(false).copy(
                onClick = { actionSink(RootAction.TrendingTabClicked) },
            )
        ),
        backNavInterceptor = null,
    )

    fun RootState.reduce(action: RootAction): RootState =
        when (action) {
            is RootAction.NewContentAvailable -> withNewContent(action.content)
            is RootAction.SearchTabClicked -> withSearchContentInStack()
            is RootAction.TrendingTabClicked -> withTrendingContentInStack()
            is RootAction.BackNavIntercepted -> withPreviousContentInStack()
        }

    private fun RootState.withNewContent(content: RootState.Content): RootState =
        copy(contentStack = contentStack.copy(current = content))
            .withConsumedContentRequest(content)

    private fun RootState.withConsumedContentRequest(content: RootState.Content): RootState =
        when (content) {
            is RootState.Content.Search -> content.state.upwardRequest.consume { withConsumedSearchRequest(it) }
            is RootState.Content.List -> content.state.upwardRequest.consume { withConsumedListRequest(it) }
            is RootState.Content.Details -> content.state.upwardRequest.consume { withConsumedDetailsRequest(it) }
            is RootState.Content.Trending -> this
        } ?: this

    private fun RootState.withConsumedSearchRequest(request: SearchState.Request): RootState =
        when (request) {
            is SearchState.Request.ShowListContent -> withListContentInStack(request.args)
        }

    private fun RootState.withConsumedListRequest(request: ListState.Request): RootState =
        when (request) {
            is ListState.Request.ShowDetailsContent -> withDetailsContentInStack(request.args)
            is ListState.Request.ShowPreviousContent -> withPreviousContentInStack()
        }

    private fun RootState.withConsumedDetailsRequest(request: DetailsState.Request): RootState =
        when (request) {
            is DetailsState.Request.ShowListContent -> withListContentInStack(request.args)
            is DetailsState.Request.ShowPreviousContent -> withPreviousContentInStack()
        }

    private fun RootState.withSearchContentInStack(): RootState =
        if (contentStack.current !is RootState.Content.Search) {
            withReplacedContentInStack(getSearchStackItem())
                .withTabBarSelection(search = true)
        } else this

    private fun getSearchStackItem(): SubstateStackItem<RootState.Content> =
        SubstateStackItem(
            observableState = substateProvider.getSearchState()
                .map { RootState.Content.Search(it) },
            observationScope = getChildScope(),
        )

    private fun RootState.withTrendingContentInStack(): RootState =
        if (contentStack.current !is RootState.Content.Trending) {
            val trendingScope = getChildScope()
            val trendingState = substateProvider.getTrendingState(trendingScope)
                .map { RootState.Content.Trending(it) }
            withReplacedContentInStack(SubstateStackItem(trendingState, trendingScope))
                .withTabBarSelection(trending = true)
        } else this

    private fun RootState.withTabBarSelection(
        search: Boolean = false,
        trending: Boolean = false,
    ): RootState =
        copy(
            tabBar = tabBar.copy(
                search = tabBar.search.withSelection(search),
                trending = tabBar.trending.withSelection(trending),
            )
        )

    private fun RootState.TabBar.Tab.withSelection(selected: Boolean): RootState.TabBar.Tab =
        Colors.run {
            copy(
                bgColor = if (selected) SecondaryDisabled else Secondary,
                contentColor = if (selected) OnPrimary else Tertiary,
            )
        }

    private fun RootState.withListContentInStack(args: ListArgs): RootState {
        val listScope = getChildScope()
        val listState = substateProvider.getListState(listScope, args)
            .map { RootState.Content.List(it) }
        return withAddedContentInStack(SubstateStackItem(listState, listScope))
    }

    private fun RootState.withDetailsContentInStack(args: DetailsArgs): RootState {
        val detailsScope = getChildScope()
        val detailsState = substateProvider.getDetailsState(detailsScope, args)
            .map { RootState.Content.Details(it) }
        return withAddedContentInStack(SubstateStackItem(detailsState, detailsScope))
    }

    private fun RootState.withReplacedContentInStack(stackItem: SubstateStackItem<RootState.Content>) =
        withNewContentStack(contentStack.replace(stackItem, ::onNewSubstate))

    private fun RootState.withAddedContentInStack(stackItem: SubstateStackItem<RootState.Content>) =
        withNewContentStack(contentStack.push(stackItem, ::onNewSubstate))

    private fun RootState.withPreviousContentInStack(): RootState =
        withNewContentStack(contentStack.pop(::onNewSubstate))

    private fun RootState.withNewContentStack(contentStack: SubstateStack<RootState.Content>): RootState =
        copy(
            contentStack = contentStack,
            backNavInterceptor = { actionSink(RootAction.BackNavIntercepted) }
                .takeIf { contentStack.backstack.size > 1 }
        )

    private fun onNewSubstate(newSubstate: RootState.Content) =
        actionSink(RootAction.NewContentAvailable(newSubstate))

    private fun getChildScope(): CoroutineScope =
        CoroutineScope(featureScope.coroutineContext + SupervisorJob())
}
