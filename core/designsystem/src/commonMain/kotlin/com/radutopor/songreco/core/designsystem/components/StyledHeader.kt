package com.radutopor.songreco.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import com.radutopor.songreco.core.designsystem.elements.Colors
import com.radutopor.songreco.core.designsystem.elements.Dimen
import com.radutopor.songreco.core.designsystem.elements.Shapes
import com.radutopor.songreco.core.designsystem.elements.TextStyles
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import songreco.core.designsystem.generated.resources.Res
import songreco.core.designsystem.generated.resources.arrow_back
import songreco.core.designsystem.generated.resources.btn_back

@Composable
fun StyledHeader(
    text: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
) =
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .shadow(
                elevation = Dimen.Elevation1,
                shape = Shapes.RoundedCorners,
            )
            .background(color = Colors.Secondary),
    ) {
        onBackClick?.let {
            Box(
                modifier = Modifier
                    .padding(end = Dimen.Spacing8)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable(onClick = onBackClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(Dimen.Size32),
                    imageVector = vectorResource(Res.drawable.arrow_back),
                    contentDescription = stringResource(Res.string.btn_back),
                    tint = Colors.Tertiary,
                )
            }
        } ?: Spacer(modifier = Modifier.width(Dimen.Spacing24))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimen.Spacing16),
            maxLines = 1,
            text = text,
            color = Colors.Tertiary,
            style = TextStyles.Heading24,
        )
    }
