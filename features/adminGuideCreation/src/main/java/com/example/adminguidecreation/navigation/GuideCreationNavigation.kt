package com.example.adminguidecreation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.adminguidecreation.GuideCreationScreen
import kotlinx.serialization.Serializable

@Serializable
object GuideCreationRoute

fun NavController.navigateToGuideCreation() = navigate(GuideCreationRoute)

fun NavGraphBuilder.guideCreationScreen() {
    composable<GuideCreationRoute> {
        GuideCreationScreen()
    }
}