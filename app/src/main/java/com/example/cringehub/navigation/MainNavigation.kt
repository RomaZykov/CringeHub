package com.example.cringehub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature.auth.AuthRoute
import com.example.feature.auth.authScreen
import com.example.feature.onboarding.navigateToOnBoarding
import com.example.feature.onboarding.onBoardingScreen

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = AuthRoute) {
        authScreen(navController::navigateToOnBoarding)
        // TODO: Add correct lambda
        onBoardingScreen()
    }
}