package com.example.adminauth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AdminAuthScreen(
    navController: NavController,
//    onAuthSuccess: () -> Unit,
    viewModel: AdminAuthViewModel = hiltViewModel<AdminAuthViewModel.Base>()
) {
    val uiState by viewModel.adminAuthScreenUiStateFlow().collectAsState()

    uiState.Show(onSignInClick = { email, password ->
        viewModel.onSignInClick(email, password, navController)
    })
//    uiState.Show(viewModel::onSignInClick, onAuthSuccess)
}