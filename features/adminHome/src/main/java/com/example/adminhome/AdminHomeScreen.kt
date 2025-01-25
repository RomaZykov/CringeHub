package com.example.adminhome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AdminHomeScreen(
    viewModel: AdminHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.state().collectAsState()

    uiState.Show()
}