package com.example.adminhome.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.toRoute
import com.example.adminnavigation.RouteBuilder

class AdminHomeRouteBuilder : RouteBuilder {

    override fun build(navController: NavHostController) {
        navController.createGraph(AdminHomeRoute) {
            composable<AdminHomeRoute> {
                it.toRoute<AdminHomeRoute>().Content(navController = navController)
            }
        }
    }
}