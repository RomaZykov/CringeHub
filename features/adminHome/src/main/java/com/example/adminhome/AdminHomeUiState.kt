package com.example.adminhome

import androidx.compose.runtime.Composable

interface AdminHomeUiState {

    @Composable
    fun Show(onGuideCreationClicked: () -> Unit)
}