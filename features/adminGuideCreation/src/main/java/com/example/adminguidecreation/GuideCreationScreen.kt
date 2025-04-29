package com.example.adminguidecreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GuideCreationScreen(
    modifier: Modifier = Modifier.semantics {
        contentDescription = GuideCreationUiState.GUIDE_CREATION_SCREEN
    },
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