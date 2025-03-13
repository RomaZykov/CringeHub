package com.example.adminauth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.adminauth.AdminAuthScreen
import com.example.adminnavigation.Route
import kotlinx.serialization.Serializable

@Serializable
object AdminAuthRoute : Route {
    @Composable
    override fun Content(navController: NavController) {
        AdminAuthScreen(navController)
    }
}

//fun NavController.navigateToAdminAuth() = navigate(AdminAuthRoute)
//
//fun NavGraphBuilder.adminAuthScreen(onAuthSuccess: () -> Unit) {
//    composable<AdminAuthRoute> {
//        AdminAuthScreen(onAuthSuccess)
//    }
//}
