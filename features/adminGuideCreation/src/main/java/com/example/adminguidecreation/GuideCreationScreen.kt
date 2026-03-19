package com.example.adminGuideCreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adminGuideCreation.model.EditableGuideUi

@Composable
fun GuideCreationScreen(
    guideId: String,
    popBackStack: () -> Unit,
    viewModel: GuideCreationViewModel = hiltViewModel<GuideCreationViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(guideId) {
        viewModel.loadGuideWithId(guideId)
    }

    GuideCreationScreenUi(
        uiState,
        popBackStack,
        viewModel::saveContent,
        viewModel::onPublishClicked
    )
}

@Composable
internal fun GuideCreationScreenUi(
    uiState: GuideCreationUiState,
    popBackStack: () -> Unit,
    onSaveContent: (EditableGuideUi) -> Unit,
    onPublishClicked: () -> Unit
) {
    uiState.Show(
        guideCreationUserActions = object : GuideCreationUserActions {
            override fun onPublishClicked() {
                onPublishClicked.invoke()
            }

            override fun popBackStack() {
                popBackStack.invoke()
            }
        },
        onSaveContent = {
            onSaveContent(it)
//                it.guideId,
//                it.title,
//                it.content.mapValues { content ->
//                    content.value.joinToString("\n") { data -> data.content }
//                },
//                media = it.content.values.filterIsInstance<ContentItem.Media>()
//                    .map { mediaData -> mediaData.content.toUri() }
        },
    )
}