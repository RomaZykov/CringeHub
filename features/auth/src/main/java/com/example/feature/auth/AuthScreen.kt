package com.example.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.state().collectAsStateWithLifecycle()

    val activityContext = LocalContext.current
    uiState.Show(
        onSignInClick = viewModel::onSignInClick,
        authScreenContext = activityContext,
        onAuthSuccess = onAuthSuccess
    )
}

@Preview(showSystemUi = true, locale = "en")
@Composable
internal fun AuthScreenPreview() {
    AuthUiState.Initial.Show({}, LocalContext.current, {})
}