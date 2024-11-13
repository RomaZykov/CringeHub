package com.example.auth

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable

data class AuthUiState(val dummy: String = "") {

    @Composable
    fun GoogleSignInButton() = Button(onClick = {}) { }
}