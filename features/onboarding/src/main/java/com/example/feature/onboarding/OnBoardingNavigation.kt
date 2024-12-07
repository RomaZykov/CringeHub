package com.example.feature.onboarding

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object OnBoardingRoute

fun NavController.navigateToOnBoarding() = navigate(OnBoardingRoute)