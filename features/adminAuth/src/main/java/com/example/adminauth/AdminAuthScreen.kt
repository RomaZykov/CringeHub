package com.example.adminauth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AdminAuthScreen(
    modifier: Modifier = Modifier.semantics {
        AdminAuthUiState.ADMIN_AUTH_SCREEN
    },
    navController: NavController,
    viewModel: AdminAuthViewModel = hiltViewModel<AdminAuthViewModel.Base>()
) {
    val uiState by viewModel.adminAuthScreenUiStateFlow().collectAsState()

    uiState.Show(onSignInClick = { email, password ->
        viewModel.onSignInClick(email, password, navController)
    })
}