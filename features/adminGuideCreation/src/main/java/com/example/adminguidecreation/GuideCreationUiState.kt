package com.example.adminGuideCreation

import androidx.compose.runtime.Composable
import com.example.adminGuideCreation.model.EditableGuideUi

internal interface GuideCreationUiState {

    @Composable
    fun Show(
        guideCreationUserActions: GuideCreationUserActions,
        onSaveContent: (EditableGuideUi) -> Unit,
    )

    companion object Semantics {
        const val GUIDE_CREATION_SCREEN = "GuideCreationScreen"

        const val EDITOR_CONTROL = "editor_control"
        const val TITLE = "title"
        const val DIALOG = "dialog"

        const val PUBLISH_BUTTON = "publish_button"
        const val BACK_BUTTON = "back_button"
        const val CREATE_PAGE_BUTTON = "create_page_button"

        const val BOLD_BUTTON = "bold_button"
        const val QUOTE_BUTTON = "quote_button"
        const val IMAGE_BUTTON = "image_button"
    }
}
