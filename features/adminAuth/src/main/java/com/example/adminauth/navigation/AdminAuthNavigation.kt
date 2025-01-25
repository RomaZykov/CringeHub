package com.example.adminauth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.adminauth.AdminAuthScreen
import kotlinx.serialization.Serializable

@Serializable
object AdminAuthRoute

fun NavController.navigateToAdminAuth() = navigate(AdminAuthRoute)

fun NavGraphBuilder.adminAuthScreen(onAuthSuccess: () -> Unit) {
    composable<AdminAuthRoute> {
        AdminAuthScreen(onAuthSuccess)
    }
}
