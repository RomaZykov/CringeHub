package com.example.adminhome.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.adminnavigation.RouteBuilder

class AdminHomeRouteBuilder : RouteBuilder {

    override fun build(navGraphBuilder: NavGraphBuilder, navController: NavHostController) {
        navGraphBuilder.composable<AdminHomeRoute> {
            it.toRoute<AdminHomeRoute>().Content(navController = navController)
        }
    }
}