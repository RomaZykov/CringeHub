package com.example.cringehub.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PrimaryYellow,
    secondary = PrimaryBlack,
    tertiary = PrimaryGray,
    // Google button
    background = GoogleBackgroundGray,
    onBackground = GoogleFontBlack
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlack,
    secondary = PrimaryYellow,
    tertiary = PrimaryGray,
    surfaceBright = PrimaryWhite,
    // Google button
    background = GoogleBackgroundBlack,
    onBackground = GoogleFontWhite
)

internal val LocalCustomColorScheme = staticCompositionLocalOf { LightColorScheme }

@Composable
fun CringeHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    WindowInsets.safeDrawing
    val colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    CompositionLocalProvider(
        LocalCustomColorScheme provides colorScheme,
        LocalCustomTypography provides CringeHubTypography(),
        LocalSpacing provides Dimensions(),
        content = content
    )
}

object CringeHubTheme {
    val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = LocalCustomColorScheme.current
    val typography: CringeHubTypography
        @Composable @ReadOnlyComposable
        get() = LocalCustomTypography.current
    val dimensions: Dimensions
        @Composable @ReadOnlyComposable
        get() = LocalSpacing.current
}
