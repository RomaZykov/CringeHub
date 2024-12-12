package com.example.cringehub.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

private val thin = Font(R.font.m_plus_rounded1c_thin, FontWeight.W100)
private val light = Font(R.font.m_plus_rounded1c_light, FontWeight.W300)
private val regular = Font(R.font.m_plus_rounded1c_regular, FontWeight.W400)
private val medium = Font(R.font.m_plus_rounded1c_medium, FontWeight.W500)
private val extraBold = Font(R.font.m_plus_rounded1c_extra_bold, FontWeight.W800)
private val black = Font(R.font.m_plus_rounded1c_black, FontWeight.W900)

// For Google button text
private val robotoMedium = Font(R.font.roboto_medium, FontWeight.W500)

internal val cringeHubFontFamily = FontFamily(thin, light, regular, medium, extraBold, black)
internal val googleButtonFontFamily = FontFamily(robotoMedium)