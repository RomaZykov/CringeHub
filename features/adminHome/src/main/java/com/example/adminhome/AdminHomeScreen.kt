package com.example.adminhome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.adminguidecreation.navigation.navigateToGuideCreation

@Composable
fun AdminHomeScreen(
    navController: NavController,
    viewModel: AdminHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.state().collectAsState()

    uiState.Show(navController::navigateToGuideCreation)
}