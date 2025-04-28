package com.example.adminauth.navigation

import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.toRoute
import com.example.adminnavigation.RouteBuilder

class AdminAuthRouteBuilder : RouteBuilder {
    override fun build(navController: NavHostController) {
        navController.createGraph(AdminAuthRoute) {
            composable<AdminAuthRoute> {
                it.toRoute<AdminAuthRoute>().Content(navController)
            }
        }
    }
}