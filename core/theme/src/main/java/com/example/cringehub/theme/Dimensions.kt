package com.example.cringehub.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @property default 0 dp.
 * @property superSmall 2 dp.
 * @property extraSmall 4 dp.
 * @property small 8 dp.
 * @property medium 16 dp.
 * @property semiLarge 24 dp.
 * @property large 32 dp.
 * @property extraLarge 48 dp.
*/
data class Dimensions(
    val default: Dp = 0.dp,
    val superSmall: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val semiLarge: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 48.dp
)

val LocalSpacing = compositionLocalOf { Dimensions() }