package com.radutopor.songreco.features.root.impl

import androidx.compose.ui.graphics.Color
import com.radutopor.songreco.core.arch.substatestack.SubstateStack
import com.radutopor.songreco.features.details.impl.DetailsState
import com.radutopor.songreco.features.list.impl.ListState
import com.radutopor.songreco.features.search.impl.SearchState
import com.radutopor.songreco.features.trending.impl.TrendingState

data class RootState(
    val contentStack: SubstateStack<Content>,
    val tabBar: TabBar,
    val backNavInterceptor: (() -> Unit)?,
) {
    sealed class Content {
        data class Search(val state: SearchState) : Content()
        data class Trending(val state: TrendingState) : Content()
        data class List(val state: ListState) : Content()
        data class Details(val state: DetailsState) : Content()
    }

    data class TabBar(
        val search: Tab,
        val trending: Tab,
    ) {
        data class Tab(
            val bgColor: Color,
            val contentColor: Color,
            val onClick: () -> Unit,
        )
    }
}
