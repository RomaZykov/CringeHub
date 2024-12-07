package com.example.cringehub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature.auth.AuthGraphRoute
import com.example.feature.auth.authGraph

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = AuthGraphRoute) {
        authGraph(navController)
    }
}