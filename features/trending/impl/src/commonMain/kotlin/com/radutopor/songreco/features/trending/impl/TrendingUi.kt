package com.radutopor.songreco.features.trending.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.radutopor.songreco.core.arch.stringvalue.asString
import com.radutopor.songreco.core.designsystem.components.StyledError
import com.radutopor.songreco.core.designsystem.components.StyledHeader
import com.radutopor.songreco.core.designsystem.components.StyledProgressIndicator
import com.radutopor.songreco.core.designsystem.elements.Colors
import com.radutopor.songreco.core.designsystem.elements.Dimen
import com.radutopor.songreco.core.designsystem.elements.Shapes
import com.radutopor.songreco.core.designsystem.elements.TextStyles
import org.jetbrains.compose.resources.stringResource
import songreco.features.trending.impl.generated.resources.Res
import songreco.features.trending.impl.generated.resources.title_trending

@Composable
fun TrendingUi(
    state: TrendingState,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimen.Spacing16),
    ) {
        Header()
        Body(state)
    }

@Composable
private fun Header(
    modifier: Modifier = Modifier,
) =
    StyledHeader(
        modifier = modifier,
        text = stringResource(Res.string.title_trending),
    )

@Composable
private fun Body(
    state: TrendingState,
    modifier: Modifier = Modifier,
) =
    when (state) {
        is TrendingState.Loading -> Loading(modifier)
        is TrendingState.Loaded -> Loaded(state, modifier)
        is TrendingState.Error -> Error(state, modifier)
    }

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
) =
    StyledProgressIndicator(
        modifier = modifier,
    )

@Composable
private fun Loaded(
    state: TrendingState.Loaded,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = Dimen.Spacing12),
    ) {
        items(state.items) { Item(it) }
    }
}

@Composable
private fun Item(
    state: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Dimen.Spacing8)
            .background(
                color = Colors.Tertiary,
                shape = Shapes.RoundedCorners,
            )
            .padding(
                horizontal = Dimen.Spacing16,
                vertical = Dimen.Spacing12,
            ),
        text = state,
        style = TextStyles.Heading18,
    )
}

@Composable
private fun Error(
    state: TrendingState.Error,
    modifier: Modifier = Modifier,
) =
    StyledError(
        modifier = modifier,
        message = state.message.asString(),
        onRetryClick = state.onRetryClick,
    )