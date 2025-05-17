package com.example.adminguidecreation

import androidx.compose.runtime.Composable
import com.example.adminguidecreation.model.GuideUi

interface GuideCreationUiState {

    @Composable
    fun Show(
        popBackStack: () -> Unit,
        saveContent: (GuideUi) -> Unit,
        onPublishClicked: () -> Unit
    )

    // Content descriptions for semantic provided for tests
    companion object {
        const val GUIDE_CREATION_SCREEN = "GuideCreationScreen"

        const val EDITOR_CONTROL = "editor_control"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val DIALOG = "dialog"

        const val PUBLISH_BUTTON = "publish_button"
        const val BACK_BUTTON = "back_button"

        const val BOLD_BUTTON = "bold_button"
        const val QUOTE_BUTTON = "quote_button"
    }
}
