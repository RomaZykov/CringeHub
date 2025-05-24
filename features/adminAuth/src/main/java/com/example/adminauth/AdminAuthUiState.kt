package com.example.adminauth

import androidx.compose.runtime.Composable

interface AdminAuthUiState {

    @Composable
    fun Show(onSignInClick: (String, String) -> Unit)

    companion object {
        const val ADMIN_AUTH_SCREEN = "AdminAuthScreen"
    }
}
