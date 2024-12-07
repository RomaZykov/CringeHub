package com.example.feature.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature.onboarding.navigateToOnBoarding
import kotlinx.serialization.Serializable

@Serializable
object AuthRoute

fun NavController.navigateToAuth() = navigate(AuthRoute)

fun NavGraphBuilder.authScreen(navController: NavHostController) {
    composable<AuthRoute> {
        AuthScreen(navController::navigateToOnBoarding)
    }
}
