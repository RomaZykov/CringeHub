package com.example.adminguidecreation

import androidx.compose.runtime.Composable

interface GuideCreationUiState {

    @Composable
    fun Show(
        popBackStack: () -> Unit,
        saveContent: (String, String) -> Unit,
        onPublishClicked: () -> Unit
    )

    // Content descriptions for semantic provided for tests
    companion object {
        const val EDITOR_CONTROL = "editor control"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val DIALOG = "dialog"
        const val PUBLISH_BUTTON = "publish button"
        const val BACK_BUTTON = "back_button"

        const val BOLD_BUTTON = "bold_button"
    }
}
