package com.radutopor.songreco.features.root.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import com.radutopor.songreco.core.designsystem.elements.Colors
import com.radutopor.songreco.core.designsystem.elements.Dimen
import com.radutopor.songreco.core.designsystem.elements.TextStyles
import com.radutopor.songreco.features.details.impl.DetailsUi
import com.radutopor.songreco.features.list.impl.ListUi
import com.radutopor.songreco.features.search.impl.SearchUi
import com.radutopor.songreco.features.trending.impl.TrendingUi
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import songreco.features.root.impl.generated.resources.Res
import songreco.features.root.impl.generated.resources.btn_search
import songreco.features.root.impl.generated.resources.btn_trending
import songreco.features.root.impl.generated.resources.search
import songreco.features.root.impl.generated.resources.trending

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RootUi(
    state: RootState,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Colors.Primary)
            .padding(top = Dimen.Spacing24),
    ) {
        Content(
            modifier = Modifier.weight(1f),
            content = state.contentStack.current,
        )
        TabBar(state.tabBar)
        BottomSpacer()
        state.backNavInterceptor?.let { BackHandler(onBack = it) }
    }

@Composable
private fun Content(
    content: RootState.Content,
    modifier: Modifier = Modifier,
) =
    when (content) {
        is RootState.Content.Search -> SearchUi(content.state, modifier)
        is RootState.Content.Trending -> TrendingUi(content.state, modifier)
        is RootState.Content.List -> ListUi(content.state, modifier)
        is RootState.Content.Details -> DetailsUi(content.state, modifier)
    }

@Composable
private fun TabBar(
    state: RootState.TabBar,
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Tab(
            text = stringResource(Res.string.btn_search),
            icon = Res.drawable.search,
            state = state.search,
        )
        Tab(
            text = stringResource(Res.string.btn_trending),
            icon = Res.drawable.trending,
            state = state.trending,
        )
    }

@Composable
private fun RowScope.Tab(
    text: String,
    icon: DrawableResource,
    state: RootState.TabBar.Tab,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier
            .weight(1f)
            .background(color = state.bgColor)
            .clickable(onClick = state.onClick)
            .padding(Dimen.Spacing16),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(Dimen.Size32),
            imageVector = vectorResource(icon),
            tint = state.contentColor,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(top = Dimen.Spacing4),
            text = text,
            color = state.contentColor,
            style = TextStyles.Heading14,
        )
    }

@Composable
private fun BottomSpacer(
    modifier: Modifier = Modifier,
) =
    Spacer(
        modifier = modifier
            .height(Dimen.Spacing24)
            .fillMaxWidth()
            .background(color = Colors.Secondary),
    )