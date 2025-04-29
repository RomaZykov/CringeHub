package com.example.adminhome

import androidx.compose.runtime.Composable

interface AdminHomeUiState {

    @Composable
    fun Show(initWhenAnyGuidesExist: () -> Unit, onGuideCreationClicked: () -> Unit)

    companion object {
        const val ADMIN_HOME_SCREEN = "AdminHomeScreen"
    }
}