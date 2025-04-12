package com.example.cringehub.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class CringeHubTypography(
    val title: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontFamily = cringeHubFontFamily,
        fontWeight = FontWeight.Bold,
        color = PrimaryBlack
    ),
    val body: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = cringeHubFontFamily,
        fontWeight = FontWeight.Normal,
        color = PrimaryBlack
    ),
    val googleButton: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = googleButtonFontFamily,
        fontWeight = FontWeight.Normal
    ),
    val onContainerBody: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = cringeHubFontFamily,
        fontWeight = FontWeight.ExtraBold,
        color = PrimaryWhite
    )
)

internal val LocalCustomTypography = staticCompositionLocalOf {
    CringeHubTypography()
}