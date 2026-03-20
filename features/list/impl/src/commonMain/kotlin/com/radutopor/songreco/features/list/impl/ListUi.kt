package com.radutopor.songreco.features.list.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun ListUi(
    state: ListState,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimen.Spacing16),
    ) {
        Header(state.header)
        Body(state.body)
    }

@Composable
private fun Header(
    state: ListState.Header,
    modifier: Modifier = Modifier,
) =
    StyledHeader(
        modifier = modifier,
        text = stringResource(state.title),
        onBackClick = state.onBackClick,
    )

@Composable
private fun Body(
    state: ListState.Body,
    modifier: Modifier = Modifier,
) =
    when (state) {
        is ListState.Body.Loading -> Loading(modifier)
        is ListState.Body.Loaded -> Loaded(state, modifier)
        is ListState.Body.Error -> Error(state, modifier)
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
    state: ListState.Body.Loaded,
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
    state: ListState.Body.Loaded.Item,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Dimen.Spacing8)
            .background(
                color = Colors.Tertiary,
                shape = Shapes.RoundedCorners,
            )
            .clickable(onClick = state.onClick)
            .padding(
                horizontal = Dimen.Spacing16,
                vertical = Dimen.Spacing12,
            ),
    ) {
        Text(
            text = state.name,
            style = TextStyles.Heading18,
        )
        Text(
            text = state.artist,
            style = TextStyles.Body14,
        )
    }
}

@Composable
private fun Error(
    state: ListState.Body.Error,
    modifier: Modifier = Modifier,
) =
    StyledError(
        modifier = modifier,
        message = state.message.asString(),
        onRetryClick = state.onRetryClick,
    )