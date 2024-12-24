package com.example.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.cringehub.theme.CringeHubTheme

@Composable
fun OnBoardingScreen(
    onBoardingUiState: OnBoardingUiState,
    onHomeRedirect: () -> Unit
) {
    onBoardingUiState.Show(onHomeRedirect)
}

@Preview(showSystemUi = true)
@Composable
internal fun OnBoardingScreenPreview() {
    CringeHubTheme {
        OnBoardingScreen(OnBoardingUiState.Initial) {}
    }
}