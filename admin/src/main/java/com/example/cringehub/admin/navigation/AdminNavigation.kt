package com.example.cringehub.admin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.adminauth.navigation.AdminAuthRoute
import com.example.adminauth.navigation.adminAuthScreen
import com.example.adminguidecreation.navigation.guideCreationScreen
import com.example.adminhome.navigation.AdminHomeRoute
import com.example.adminhome.navigation.adminHomeScreen
import com.example.adminhome.navigation.navigateToAdminHome
import kotlinx.serialization.Serializable

@Composable
fun AdminNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = AdminAuthRoute) {
        adminAuthScreen(navController::navigateToAdminHome)
        adminNestedNavigation(navController)
    }
}

@Serializable
object AdminHomeNestedGraph

fun NavGraphBuilder.adminNestedNavigation(navController: NavHostController) {
    navigation<AdminHomeNestedGraph>(startDestination = AdminHomeRoute) {
        adminHomeScreen(navController)
        guideCreationScreen()
    }
}
