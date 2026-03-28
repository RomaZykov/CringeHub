package com.example.adminGuideCreation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.adminGuideCreation.GuideCreationScreen
import com.example.adminnavigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class GuideCreationRoute(private val guideId: String = "") : Route {
    @Composable
    override fun Content(navController: NavController) {
        GuideCreationScreen(guideId = guideId, popBackStack = navController::popBackStack)
    }
}