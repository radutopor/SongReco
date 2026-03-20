package com.radutopor.songreco.core.arch.stringvalue

import androidx.compose.runtime.Composable
import com.radutopor.songreco.core.arch.stringvalue.StringValue.Res
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Composed in states to abstract either a [String] or a [StringResource].
 */
sealed class StringValue {
    data class Str(val value: String) : StringValue()
    data class Res(val value: StringResource) : StringValue()
}

/**
 * Represents this [String] as a [StringValue].
 */
fun String.toStringValue(): StringValue = StringValue.Str(this)

/**
 * Represents this [StringResource] as a [StringValue].
 */
fun StringResource.toStringValue(): StringValue = StringValue.Res(this)

/**
 * Get the absolute string representation of this [StringValue] in a Composable context.
 */
@Composable
fun StringValue.asString(): String =
    when (this) {
        is StringValue.Str -> value
        is Res -> stringResource(value)
    }