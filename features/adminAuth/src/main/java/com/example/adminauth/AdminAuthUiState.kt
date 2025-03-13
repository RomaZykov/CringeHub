package com.example.adminauth

import androidx.compose.runtime.Composable

interface AdminAuthUiState {

    @Composable
    fun Show(onSignInClick: (String, String) -> Unit)
}
