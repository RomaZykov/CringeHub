package com.example.feature.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object OnBoardingRoute

fun NavController.navigateToOnBoarding() = navigate(OnBoardingRoute)

// TODO: Parameter: onHomeRedirect: () -> Unit
fun NavGraphBuilder.onBoardingScreen() {
    composable<OnBoardingRoute> {
        OnBoardingScreen()
    }
}