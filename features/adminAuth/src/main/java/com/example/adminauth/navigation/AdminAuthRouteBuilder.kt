package com.example.adminauth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.adminnavigation.RouteBuilder

class AdminAuthRouteBuilder : RouteBuilder {
    override fun build(navGraphBuilder: NavGraphBuilder, navController: NavHostController) {
        navGraphBuilder.composable<AdminAuthRoute> {
            it.toRoute<AdminAuthRoute>().Content(navController = navController)
        }
    }
}