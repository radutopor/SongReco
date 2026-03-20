package com.radutopor.songreco.core.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.radutopor.songreco.core.designsystem.elements.Colors
import com.radutopor.songreco.core.designsystem.elements.Dimen
import com.radutopor.songreco.core.designsystem.elements.Shapes
import com.radutopor.songreco.core.designsystem.elements.TextStyles

@Composable
fun StyledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) =
    StyledButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        Text(
            text = text,
            style = TextStyles.Heading18
                // override default color, determined dynamically through buttonColors
                .copy(color = Color.Unspecified),
        )
    }

@Composable
fun StyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) =
    Button(
        modifier = modifier,
        shape = Shapes.RoundedCorners,
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Secondary,
            contentColor = Colors.Tertiary,
            disabledContainerColor = Colors.SecondaryDisabled,
            disabledContentColor = Colors.OnPrimary,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = Dimen.Elevation1,
            pressedElevation = Dimen.Elevation0,
            disabledElevation = Dimen.Elevation0,
        ),
        contentPadding = PaddingValues(Dimen.Spacing12),
        enabled = enabled,
        onClick = onClick,
        content = content,
    )