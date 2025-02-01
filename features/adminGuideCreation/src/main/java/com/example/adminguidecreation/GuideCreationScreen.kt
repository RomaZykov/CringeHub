package com.example.adminguidecreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohamedrejeb.richeditor.model.rememberRichTextState

@Composable
fun GuideCreationScreen(
    viewModel: GuideCreationViewModel = hiltViewModel()
) {
    val uiState by viewModel.state().collectAsState()
    val titleState = rememberRichTextState()
    val contentState = rememberRichTextState()

    uiState.Show(titleState, contentState, viewModel::saveContent, viewModel::onPublishClicked)
}