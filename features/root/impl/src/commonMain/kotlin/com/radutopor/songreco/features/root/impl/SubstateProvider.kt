package com.radutopor.songreco.features.root.impl

import com.radutopor.songreco.features.details.api.DetailsArgs
import com.radutopor.songreco.features.details.impl.DetailsState
import com.radutopor.songreco.features.details.impl.DetailsStore
import com.radutopor.songreco.features.list.api.ListArgs
import com.radutopor.songreco.features.list.impl.ListState
import com.radutopor.songreco.features.list.impl.ListStore
import com.radutopor.songreco.features.search.impl.SearchState
import com.radutopor.songreco.features.search.impl.SearchStore
import com.radutopor.songreco.features.trending.impl.TrendingState
import com.radutopor.songreco.features.trending.impl.TrendingStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

internal interface SubstateProvider {
    fun getSearchState(): StateFlow<SearchState>
    fun getTrendingState(substateScope: CoroutineScope): StateFlow<TrendingState>
    fun getListState(substateScope: CoroutineScope, args: ListArgs): StateFlow<ListState>
    fun getDetailsState(substateScope: CoroutineScope, args: DetailsArgs): StateFlow<DetailsState>
}

internal class SubstateProviderImpl : SubstateProvider, KoinComponent {

    override fun getSearchState(): StateFlow<SearchState> =
        get<SearchStore>().state

    override fun getTrendingState(
        substateScope: CoroutineScope,
    ): StateFlow<TrendingState> =
        get<TrendingStore> { parametersOf(substateScope) }.state

    override fun getListState(
        substateScope: CoroutineScope,
        args: ListArgs,
    ): StateFlow<ListState> =
        get<ListStore> { parametersOf(substateScope, args) }.state

    override fun getDetailsState(
        substateScope: CoroutineScope,
        args: DetailsArgs,
    ): StateFlow<DetailsState> =
        get<DetailsStore> { parametersOf(substateScope, args) }.state
}
