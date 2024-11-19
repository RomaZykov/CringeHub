package com.example.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    onSignInClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    uiState.GoogleSignInButton(viewModel::onSignInClick)
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {

}