package com.example.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.state().collectAsState()

    Row(modifier = Modifier.fillMaxHeight()) {
        Box(Modifier.align(Alignment.CenterVertically)) {
            uiState.GoogleSignInButton(viewModel::onSignInClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {

}