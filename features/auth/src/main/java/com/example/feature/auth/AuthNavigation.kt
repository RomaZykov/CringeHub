package com.example.feature.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.feature.onboarding.OnBoardingRoute
import com.example.feature.onboarding.OnBoardingScreen
import com.example.feature.onboarding.navigateToOnBoarding
import kotlinx.serialization.Serializable

@Serializable
object AuthGraphRoute

@Serializable
object AuthRoute

fun NavController.navigateToAuth() = navigate(AuthRoute)

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<AuthGraphRoute>(startDestination = AuthRoute) {
        composable<AuthRoute> {
            AuthScreen(navController::navigateToOnBoarding)
        }
        composable<OnBoardingRoute> { OnBoardingScreen() }
    }
}