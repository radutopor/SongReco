package com.radutopor.songreco.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.radutopor.songreco.core.designsystem.elements.Dimen
import com.radutopor.songreco.core.designsystem.elements.TextStyles
import org.jetbrains.compose.resources.stringResource
import songreco.core.designsystem.generated.resources.Res
import songreco.core.designsystem.generated.resources.btn_retry

@Composable
fun StyledError(
    message: String,
    modifier: Modifier = Modifier,
    onRetryClick: (() -> Unit)? = null,
) =
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(
            space = Dimen.Spacing16,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = message,
            textAlign = TextAlign.Center,
            style = TextStyles.Heading24,
        )
        onRetryClick?.let {
            StyledButton(
                text = stringResource(Res.string.btn_retry),
                onClick = onRetryClick,
            )
        }
    }
