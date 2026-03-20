package com.radutopor.songreco.core.designsystem.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.radutopor.songreco.core.designsystem.elements.Colors
import com.radutopor.songreco.core.designsystem.elements.Shapes
import com.radutopor.songreco.core.designsystem.elements.TextStyles

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
) =
    TextField(
        modifier = modifier,
        singleLine = true,
        shape = Shapes.RoundedCorners,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Colors.Tertiary,
            focusedContainerColor = Colors.Tertiary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyles.Body18,
        placeholder = placeholder?.let {
            {
                Text(
                    text = placeholder,
                    color = Colors.SecondaryDisabled,
                    style = TextStyles.Body18,
                )
            }
        },
        value = value,
        onValueChange = onValueChange,
    )