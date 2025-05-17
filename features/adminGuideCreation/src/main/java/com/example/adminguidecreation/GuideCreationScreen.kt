package com.example.adminguidecreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    guideId: String,
    popBackStack: () -> Unit,
    viewModel: GuideCreationViewModel = hiltViewModel<GuideCreationViewModel.Base>()
) {
    LaunchedEffect(guideId) {
        viewModel.loadGuideWithId(guideId)
    }
    val uiState by viewModel.guideCreationUiStateFlow().collectAsState()
    uiState.Show(
        popBackStack = popBackStack,
        saveContent = {
            viewModel.saveContent(it.guideId, it.title, it.content)
        },
        onPublishClicked = viewModel::onPublishClicked
    )
}