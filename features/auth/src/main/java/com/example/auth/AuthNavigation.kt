package com.example.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavController.navigateToAuth() = navigate(AuthRoute)

fun NavGraphBuilder.authScreen() {
    composable<AuthRoute> {
        AuthScreen()
    }
}