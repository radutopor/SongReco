package com.radutopor.songreco.features.details.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import com.radutopor.songreco.core.arch.stringvalue.asString
import com.radutopor.songreco.core.arch.utils.forwardingPainter
import com.radutopor.songreco.core.designsystem.components.StyledButton
import com.radutopor.songreco.core.designsystem.components.StyledError
import com.radutopor.songreco.core.designsystem.components.StyledHeader
import com.radutopor.songreco.core.designsystem.components.StyledProgressIndicator
import com.radutopor.songreco.core.designsystem.elements.Colors
import com.radutopor.songreco.core.designsystem.elements.Dimen
import com.radutopor.songreco.core.designsystem.elements.Shapes
import com.radutopor.songreco.core.designsystem.elements.TextStyles
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import songreco.features.details.impl.generated.resources.Res
import songreco.features.details.impl.generated.resources.album
import songreco.features.details.impl.generated.resources.btn_similar
import songreco.features.details.impl.generated.resources.desc_album_art
import songreco.features.details.impl.generated.resources.label_album
import songreco.features.details.impl.generated.resources.label_duration
import songreco.features.details.impl.generated.resources.title_details

@Composable
fun DetailsUi(
    state: DetailsState,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimen.Spacing16),
    ) {
        Header(state.onBackClick)
        Body(state.body)
    }

@Composable
private fun Header(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) =
    StyledHeader(
        modifier = modifier,
        text = stringResource(Res.string.title_details),
        onBackClick = onBackClick,
    )

@Composable
private fun Body(
    state: DetailsState.Body,
    modifier: Modifier = Modifier,
) =
    when (state) {
        is DetailsState.Body.Loading -> Loading(modifier)
        is DetailsState.Body.Loaded -> Loaded(state, modifier)
        is DetailsState.Body.Error -> Error(state, modifier)
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
    state: DetailsState.Body.Loaded,
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimen.Spacing8),
    ) {
        AlbumArt(
            url = state.albumArtUrl,
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = Colors.Tertiary,
                    shape = Shapes.RoundedCorners,
                )
                .padding(Dimen.Spacing16),
        ) {
            CenterField(
                text = state.artist,
                style = TextStyles.Heading14,
            )
            CenterField(
                modifier = Modifier.padding(top = Dimen.Spacing4),
                text = state.name,
                style = TextStyles.Heading24,
            )
            LabeledField(
                modifier = Modifier.padding(top = Dimen.Spacing16),
                label = Res.string.label_album,
                text = state.albumName,
            )
            LabeledField(
                label = Res.string.label_duration,
                text = state.duration,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SimilarButton(
            state = state.onSimilarClick,
        )
    }

@Composable
private fun AlbumArt(
    url: String,
    modifier: Modifier = Modifier,
) {
    val placeholder = forwardingPainter(
        painter = painterResource(Res.drawable.album),
        colorFilter = ColorFilter.tint(Colors.Accent),
    )
    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimen.Spacing24)
            .aspectRatio(1f)
            .clip(Shapes.RoundedCorners),
        model = url,
        placeholder = placeholder,
        fallback = placeholder,
        error = placeholder,
        contentDescription = stringResource(Res.string.desc_album_art),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun CenterField(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
) =
    Text(
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = text,
        style = style,
    )

@Composable
private fun LabeledField(
    label: StringResource,
    text: String,
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Dimen.Spacing8),
        horizontalArrangement = spacedBy(Dimen.Spacing8),
    ) {
        Text(
            modifier = Modifier.weight(0.5f),
            style = TextStyles.Body14.copy(
                fontStyle = FontStyle.Italic,
            ),
            text = stringResource(label),
        )
        Text(
            modifier = Modifier.weight(1f),
            style = TextStyles.Body14,
            text = text,
        )
    }

@Composable
private fun SimilarButton(
    state: () -> Unit,
    modifier: Modifier = Modifier,
) =
    StyledButton(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(Res.string.btn_similar),
        onClick = state,
    )

@Composable
private fun Error(
    state: DetailsState.Body.Error,
    modifier: Modifier = Modifier,
) =
    StyledError(
        modifier = modifier,
        message = state.message.asString(),
        onRetryClick = state.onRetryClick,
    )
