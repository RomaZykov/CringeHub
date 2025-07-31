package com.example.adminguidecreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adminguidecreation.components.ContentItem

@Composable
fun GuideCreationScreen(
    modifier: Modifier = Modifier.semantics {
        contentDescription = GuideCreationUiState.GUIDE_CREATION_SCREEN
    },
    guideId: String,
    popBackStack: () -> Unit,
    viewModel: GuideCreationViewModel = hiltViewModel<GuideCreationViewModel.Base>()
) {
    val uiState by viewModel.guideCreationUiStateFlow().collectAsState()
    LaunchedEffect(guideId) {
        viewModel.loadGuideWithId(guideId)
    }
    uiState.Show(
        popBackStack = popBackStack,
        saveContent = {
            viewModel.saveContent(
                it.guideId,
                it.title,
                it.content.mapValues { content ->
                    content.value.joinToString("\n") { data -> data.content }
                },
                media = it.content.values.filterIsInstance<ContentItem.Media>()
                    .map { mediaData -> mediaData.content.toUri() }
            )
        },
        onPublishClicked = viewModel::onPublishClicked
    )
}