package com.example.adminhome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AdminHomeScreen(
    modifier: Modifier = Modifier.semantics {
        contentDescription = AdminHomeUiState.ADMIN_HOME_SCREEN
    },
    navController: NavController,
    viewModel: AdminHomeViewModel = hiltViewModel<AdminHomeViewModel.Base>()
) {
    val uiState by viewModel.adminHomeUiStateFlow().collectAsState()

    uiState.Show(
        initWhenAnyGuidesExist = viewModel::init,
        onGuideCreationClicked = {
            viewModel.navigateToGuideCreation(navController)
        },
        onDraftClicked = {
            viewModel.navigateToDraft(navController, it)
        },
        onDraftDeleted = {
            viewModel.deleteGuide(it)
        }
    )
}