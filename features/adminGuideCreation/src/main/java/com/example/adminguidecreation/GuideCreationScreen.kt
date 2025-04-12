package com.example.adminguidecreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GuideCreationScreen(
    popBackStack: () -> Unit,
    viewModel: GuideCreationViewModel = hiltViewModel<GuideCreationViewModel.Base>()
) {
    val uiState by viewModel.guideCreationUiStateFlow().collectAsState()

    uiState.Show(
        popBackStack,
        viewModel::saveContent,
        viewModel::onPublishClicked
    )
}