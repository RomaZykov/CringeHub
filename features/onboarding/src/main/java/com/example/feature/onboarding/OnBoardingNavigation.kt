package com.example.feature.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object OnBoardingRoute

fun NavController.navigateToOnBoarding() = navigate(OnBoardingRoute)
fun NavController.navigateToHome() = navigate(OnBoardingRoute)

fun NavGraphBuilder.onBoardingScreen(
    onHomeRedirect: () -> Unit
) {
    composable<OnBoardingRoute> {
        OnBoardingScreen(OnBoardingUiState.Initial, onHomeRedirect)
    }
}