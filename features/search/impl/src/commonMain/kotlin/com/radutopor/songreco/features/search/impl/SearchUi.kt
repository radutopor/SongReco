package com.radutopor.songreco.features.search.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.radutopor.songreco.core.designsystem.components.StyledButton
import com.radutopor.songreco.core.designsystem.components.StyledTextField
import com.radutopor.songreco.core.designsystem.elements.Colors
import com.radutopor.songreco.core.designsystem.elements.Dimen
import com.radutopor.songreco.core.designsystem.elements.TextStyles
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import songreco.features.search.impl.generated.resources.Res
import songreco.features.search.impl.generated.resources.desc_search
import songreco.features.search.impl.generated.resources.hint_search
import songreco.features.search.impl.generated.resources.search
import songreco.features.search.impl.generated.resources.title_logo

@Composable
fun SearchUi(
    state: SearchState,
    modifier: Modifier = Modifier,
) =
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.Spacing24),
        contentAlignment = Alignment.CenterStart,
    ) {
        Logo()
        Search(state)
    }

@Composable
private fun Logo(
    modifier: Modifier = Modifier,
) =
    Text(
        modifier = modifier
            .padding(bottom = 192.dp)
            .background(
                color = Colors.Accent,
                shape = CircleShape,
            )
            .padding(Dimen.Spacing24),
        maxLines = 1,
        fontStyle = FontStyle.Italic,
        color = Colors.Tertiary,
        text = stringResource(Res.string.title_logo),
        style = TextStyles.Heading24,
    )

@Composable
private fun Search(
    state: SearchState,
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = spacedBy(Dimen.Spacing16),
    ) {
        SearchBox(state.searchBox, modifier = Modifier.weight(1f))
        SearchButton(state.searchButton, modifier = Modifier.fillMaxHeight())
    }

@Composable
private fun SearchBox(
    state: SearchState.SearchBox,
    modifier: Modifier = Modifier,
) =
    StyledTextField(
        modifier = modifier,
        value = state.text,
        onValueChange = state.onTextChange,
        placeholder = stringResource(Res.string.hint_search),
    )

@Composable
private fun SearchButton(
    state: SearchState.SearchButton,
    modifier: Modifier = Modifier,
) =
    StyledButton(
        modifier = modifier
            .aspectRatio(1f),
        enabled = state.enabled,
        onClick = state.onClick,
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = vectorResource(Res.drawable.search),
            contentDescription = stringResource(Res.string.desc_search),
        )
    }
