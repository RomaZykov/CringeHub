package com.example.adminguidecreation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.adminguidecreation.GuideCreationScreen
import com.example.adminnavigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class GuideCreationRoute(private val guideId: String = "") : Route {
    @Composable
    override fun Content(navController: NavController) {
        GuideCreationScreen(popBackStack = navController::popBackStack)
    }
}