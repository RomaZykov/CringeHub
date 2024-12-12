package com.example.feature.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object AuthRoute

fun NavController.navigateToAuth() = navigate(AuthRoute)

fun NavGraphBuilder.authScreen(onAuthSuccess: () -> Unit) {
    composable<AuthRoute> {
        AuthScreen(onAuthSuccess = onAuthSuccess)
    }
}
