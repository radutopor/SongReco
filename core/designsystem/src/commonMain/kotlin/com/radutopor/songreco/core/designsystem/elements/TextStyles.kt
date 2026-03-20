package com.radutopor.songreco.core.designsystem.elements

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextStyles {

    private val base = TextStyle(
        fontFamily = FontFamily.SansSerif,
        color = Colors.OnPrimary,
    )

    private val body = base.copy(
        fontWeight = FontWeight.Normal,
    )

    private val heading = base.copy(
        fontWeight = FontWeight.Bold,
    )

    val Body12 = body.copy(
        fontSize = 12.sp,
    )

    val Body14 = body.copy(
        fontSize = 14.sp,
    )

    val Body18 = body.copy(
        fontSize = 18.sp,
    )

    val Heading14 = heading.copy(
        fontSize = 14.sp,
    )

    val Heading18 = heading.copy(
        fontSize = 18.sp,
    )

    val Heading24 = heading.copy(
        fontSize = 24.sp,
    )
}