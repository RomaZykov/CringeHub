package com.example.hub

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HubRoute

fun NavController.navigateToHub() = navigate(HubRoute)

fun NavGraphBuilder.hubScreen() {
    composable<HubRoute> {
        HubScreen()
    }
}