package com.example.feature.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object OnBoardingRoute

fun NavController.navigateToOnBoarding() = navigate(OnBoardingRoute)

fun NavGraphBuilder.onBoardingScreen(navController: NavHostController) {
    composable<OnBoardingRoute> {
        OnBoardingScreen(navController::navigateToOnBoarding)
    }
}