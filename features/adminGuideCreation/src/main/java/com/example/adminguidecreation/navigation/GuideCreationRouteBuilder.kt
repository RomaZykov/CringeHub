package com.example.adminguidecreation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.adminnavigation.RouteBuilder

class GuideCreationRouteBuilder : RouteBuilder {

    override fun build(navGraphBuilder: NavGraphBuilder, navController: NavHostController) {
        navGraphBuilder.composable<GuideCreationRoute> {
            it.toRoute<GuideCreationRoute>().Content(navController = navController)
        }
    }
}