package com.example.auth

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable

interface AuthUiState {
    object Initial : AuthUiState

    object Loading : AuthUiState

    data class Auth(val dummy: String) : AuthUiState {

        @Composable
        fun GoogleSignInButton() = Button(onClick = {}) { }
    }

    data object Error : AuthUiState
}