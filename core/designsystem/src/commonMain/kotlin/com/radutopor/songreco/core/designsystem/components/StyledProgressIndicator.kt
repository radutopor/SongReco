package com.radutopor.songreco.core.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.radutopor.songreco.core.designsystem.elements.Colors

@Composable
fun StyledProgressIndicator(
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        CircularProgressIndicator(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
            color = Colors.Accent,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
