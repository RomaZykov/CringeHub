package com.example.cringehub.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.cringehub.ui.theme.PrimaryBlack
import com.example.cringehub.ui.theme.PrimaryYellow

@Composable
fun AuthScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PrimaryYellow,
                        PrimaryBlack
                    )
                )
            )
    ) {

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen()
}