package com.example.adminhome

import androidx.compose.runtime.Composable

interface AdminHomeUiState {

    @Composable
    fun Show(
        initWhenAnyGuidesExist: () -> Unit,
        onGuideCreationClicked: () -> Unit,
        onDraftClicked: (String) -> Unit
    )

    companion object {
        const val ADMIN_HOME_SCREEN = "AdminHomeScreen"

        const val DRAFT_LIST = "draft list"
    }
}