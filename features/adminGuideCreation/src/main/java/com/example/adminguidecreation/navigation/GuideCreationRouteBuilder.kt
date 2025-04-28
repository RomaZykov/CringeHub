package com.example.adminguidecreation.navigation

import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.toRoute
import com.example.adminnavigation.RouteBuilder

class GuideCreationRouteBuilder : RouteBuilder {

    override fun build(navController: NavHostController) {
        navController.createGraph(GuideCreationRoute::class) {
            composable<GuideCreationRoute> {
                it.toRoute<GuideCreationRoute>().Content(navController = navController)
            }
        }
    }
}