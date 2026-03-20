package com.radutopor.songreco.features.root.impl.testdoubles

import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.details.impl.DetailsState
import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.list.impl.ListState
import com.radutopor.songreco.features.root.impl.SubstateProvider
import com.radutopor.songreco.features.search.impl.SearchState
import com.radutopor.songreco.features.trending.impl.TrendingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class SubstateProviderFake : SubstateProvider {

    var getSearchStateReturn: () -> StateFlow<SearchState> = { MutableStateFlow(searchStateStub) }
    override fun getSearchState(): StateFlow<SearchState> = getSearchStateReturn()

    var getTrendingStateReturn: (CoroutineScope) -> StateFlow<TrendingState> = { MutableStateFlow(trendingStateStub) }
    override fun getTrendingState(substateScope: CoroutineScope): StateFlow<TrendingState> = getTrendingStateReturn(substateScope)

    var getListStateReturn: (CoroutineScope, ListArgs) -> StateFlow<ListState> = { _, _ -> MutableStateFlow(listStateStub) }
    override fun getListState(substateScope: CoroutineScope, args: ListArgs): StateFlow<ListState> = getListStateReturn(substateScope, args)

    var getDetailsStateReturn: (CoroutineScope, DetailsArgs) -> StateFlow<DetailsState> = { _, _ -> MutableStateFlow(detailsStateStub) }
    override fun getDetailsState(substateScope: CoroutineScope, args: DetailsArgs): StateFlow<DetailsState> =
        getDetailsStateReturn(substateScope, args)
}