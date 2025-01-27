package com.example.adminguidecreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GuideCreationScreen(
    viewModel: GuideCreationViewModel = hiltViewModel()
) {
    val uiState by viewModel.state().collectAsState()
}