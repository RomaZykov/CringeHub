package com.example.adminnavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface RouteBuilder {
    fun build(navGraphBuilder: NavGraphBuilder, navController: NavHostController)
}
