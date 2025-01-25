package com.example.adminauth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AdminAuthScreen(
    onAuthSuccess: () -> Unit,
    viewModel: AdminAuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.state().collectAsState()
    uiState.Show(viewModel::onSignInClick, onAuthSuccess)
}