package com.example.cringehub.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

private val thin = Font(R.font.m_plus_rounded1c_thin, FontWeight.Thin)
private val light = Font(R.font.m_plus_rounded1c_light, FontWeight.Light)
private val regular = Font(R.font.m_plus_rounded1c_regular, FontWeight.Normal)
private val medium = Font(R.font.m_plus_rounded1c_medium, FontWeight.Medium)
private val extraBold = Font(R.font.m_plus_rounded1c_extra_bold, FontWeight.ExtraBold)
private val black = Font(R.font.m_plus_rounded1c_black, FontWeight.Black)

internal val cringeHubFontFamily = FontFamily(thin, light, regular, medium, extraBold, black)

private val robotoMedium = Font(R.font.roboto_medium, FontWeight.W500)
internal val googleButtonFontFamily = FontFamily(robotoMedium)

