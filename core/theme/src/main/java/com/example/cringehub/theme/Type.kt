package com.example.cringehub.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class CringeHubTypography(
    val body: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = cringeHubFontFamily,
        fontWeight = FontWeight.Normal,
        color = SecondaryGray
    ),
    val googleButton: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = googleButtonFontFamily,
        fontWeight = FontWeight.Normal
    ),
    val title: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontFamily = cringeHubFontFamily,
        fontWeight = FontWeight.Black,
        color = PrimaryBlack
    )
)

internal val LocalCustomTypography = staticCompositionLocalOf {
    CringeHubTypography()
}