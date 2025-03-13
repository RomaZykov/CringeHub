package com.example.adminhome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AdminHomeScreen(
    navController: NavController,
    viewModel: AdminHomeViewModel = hiltViewModel<AdminHomeViewModel.Base>()
) {
    val uiState by viewModel.adminHomeUiStateFlow().collectAsState()

    uiState.Show(onGuideCreationClicked = {
        viewModel.navigateToGuideCreation(navController)
    })
}