package com.radutopor.songreco.features.root.impl.testdoubles

import androidx.compose.ui.graphics.Color
import com.radutopor.songreco.core.arch.substatestack.SubstateStack
import com.radutopor.songreco.core.arch.upwardrequest.UpwardRequest
import com.radutopor.songreco.features.details.impl.DetailsState
import com.radutopor.songreco.features.list.impl.ListState
import com.radutopor.songreco.features.root.impl.RootState
import com.radutopor.songreco.features.search.impl.SearchState
import com.radutopor.songreco.features.trending.impl.TrendingState
import songreco.core.designsystem.generated.resources.Res
import songreco.core.designsystem.generated.resources.msg_err_generic

internal val searchStateStub = SearchState(
    searchBox = SearchState.SearchBox(
        text = "",
        onTextChange = {},
    ),
    searchButton = SearchState.SearchButton(
        enabled = true,
        onClick = {},
    ),
    upwardRequest = UpwardRequest(
        onRequestConsumed = {},
    )
)

internal val searchContentStub = RootState.Content.Search(state = searchStateStub)

internal val trendingStateStub = TrendingState.Loading

internal val trendingContentStub = RootState.Content.Trending(state = trendingStateStub)

internal val listStateStub = ListState(
    header = ListState.Header(
        title = Res.string.msg_err_generic,
        onBackClick = {},
    ),
    body = ListState.Body.Loading,
    upwardRequest = UpwardRequest(
        onRequestConsumed = {},
    )
)

internal val listContentStub = RootState.Content.List(state = listStateStub)

internal val detailsStateStub = DetailsState(
    onBackClick = {},
    body = DetailsState.Body.Loading,
    upwardRequest = UpwardRequest(
        onRequestConsumed = {},
    ),
)

internal val detailsContentStub = RootState.Content.Details(state = detailsStateStub)

internal val rootStateTabBarTabStub = RootState.TabBar.Tab(
    bgColor = Color.Unspecified,
    contentColor = Color.Unspecified,
    onClick = {},
)

internal val rootStateTabBarStub = RootState.TabBar(
    search = rootStateTabBarTabStub,
    trending = rootStateTabBarTabStub,
)

internal val rootStateStub = RootState(
    contentStack = SubstateStack(
        current = searchContentStub,
        backstack = emptyList(),
    ),
    tabBar = rootStateTabBarStub,
    backNavInterceptor = null,
)