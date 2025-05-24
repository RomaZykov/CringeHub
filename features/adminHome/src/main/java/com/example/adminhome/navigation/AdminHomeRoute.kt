package com.example.adminhome.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.adminhome.AdminHomeScreen
import com.example.adminnavigation.Route
import kotlinx.serialization.Serializable

@Serializable
object AdminHomeRoute : Route {
    @Composable
    override fun Content(navController: NavController) {
        AdminHomeScreen(navController = navController)
    }
}