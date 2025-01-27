package com.example.adminhome.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.adminhome.AdminHomeScreen
import kotlinx.serialization.Serializable

@Serializable
object AdminHomeRoute

fun NavController.navigateToAdminHome() = navigate(AdminHomeRoute)

fun NavGraphBuilder.adminHomeScreen(navController: NavController) {
    composable<AdminHomeRoute> {
        AdminHomeScreen(navController)
    }
}