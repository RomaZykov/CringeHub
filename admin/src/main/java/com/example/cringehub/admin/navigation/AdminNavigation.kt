package com.example.cringehub.admin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.adminauth.navigation.AdminAuthRoute
import com.example.adminauth.navigation.adminAuthScreen
import com.example.adminhome.navigation.adminHomeScreen
import com.example.adminhome.navigation.navigateToAdminHome

@Composable
fun AdminNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = AdminAuthRoute) {
        adminAuthScreen(navController::navigateToAdminHome)
        adminHomeScreen()
    }
}
