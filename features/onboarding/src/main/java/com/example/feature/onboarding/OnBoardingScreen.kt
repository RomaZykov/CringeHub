package com.example.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun OnBoardingScreen(onBoarding: () -> Unit) {
    onBoarding.invoke()
    Box(modifier = Modifier.fillMaxSize().background(Color.Blue))
}