package com.example.cringehub.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

@Immutable
data class CringeHubTypography(
    val body: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Green),
    val googleButton: FontFamily = googleButtonFontFamily,
    val title: TextStyle = TextStyle(fontSize = 32.sp, color = Color.Red)
)

internal val LocalCustomTypography = staticCompositionLocalOf {
    CringeHubTypography()
}