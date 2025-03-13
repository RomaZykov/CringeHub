package com.example.adminauth.model

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.adminauth.AdminAuthUiState

object ErrorUi : AdminAuthUiState {
    @Composable
    override fun Show(onSignInClick: (String, String) -> Unit) {
        Text("ErrorUi")
    }
}