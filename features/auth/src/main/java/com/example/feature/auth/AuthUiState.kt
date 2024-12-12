package com.example.feature.auth

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cringehub.theme.CringeHubTheme

interface AuthUiState {

    @Composable
    fun Show(
        onSignInClick: (Context) -> Unit,
        authScreenContext: Context,
        onAuthSuccess: () -> Unit,
    )

    object Initial : AuthUiState {

        @Composable
        override fun Show(
            onSignInClick: (Context) -> Unit,
            authScreenContext: Context,
            onAuthSuccess: () -> Unit
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CringeHubTheme.colorScheme.primary)
            ) {
                ShowWelcomeLogoAndTitle()
                ShowWelcomeView()
                ShowWelcomeText()

                GoogleSignInButton {
                    onSignInClick(authScreenContext)
                }
            }

        }

        @Composable
        private fun GoogleSignInButton(onSignInClick: () -> Unit) {
            Button(
                onClick = onSignInClick,
                modifier = Modifier.requiredHeight(40.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                ),
                border = BorderStroke(1.dp, colorResource(R.color.black_google_stroke)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    modifier = Modifier.padding(12.dp, end = 10.dp),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(end = 12.dp),
                    fontFamily = CringeHubTheme.typography.googleButton,
                    text = stringResource(R.string.google_sign_in),
                    color = colorResource(R.color.black_google_font)
                )
            }
        }

        @Composable
        private fun ShowWelcomeLogoAndTitle() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CringeHubTheme.dimensions.spaceSmall)
                    .background(Color.Red)
            ) {
                val mainLogo = ImageVector.vectorResource(id = R.drawable.main_logo)
                val logoPainter = rememberVectorPainter(image = mainLogo)
                Canvas(modifier = Modifier.size(48.dp)) {
                    with(logoPainter) {
                        draw(logoPainter.intrinsicSize)
                    }
                }
                val mainTitle = ImageVector.vectorResource(id = R.drawable.main_title)
                val titlePainter = rememberVectorPainter(image = mainTitle)
                Canvas(modifier = Modifier) {
                    with(titlePainter) {
                        draw(titlePainter.intrinsicSize)
                    }
                }
            }
        }

        @Composable
        private fun ShowWelcomeView() {
            Image(
                painter = painterResource(R.drawable.login_view),
                contentDescription = ""
            )
        }

        @Composable
        private fun ShowWelcomeText() {
            ProvideTextStyle(value = CringeHubTheme.typography.title) {
                Text(
                    text = stringResource(R.string.welcome_main_title)
                )
            }
        }
    }

    object Success : AuthUiState {
        @Composable
        override fun Show(
            onSignInClick: (Context) -> Unit,
            authScreenContext: Context,
            onAuthSuccess: () -> Unit
        ) {
            onAuthSuccess()
        }
    }

    // TODO - How to handle Error correctly?
    data class Error(val message: String) : AuthUiState {
        @Composable
        override fun Show(
            onSignInClick: (Context) -> Unit,
            authScreenContext: Context,
            onAuthSuccess: () -> Unit
        ) {
            println("Bad")
        }
    }
}

@Preview(showBackground = true, locale = "en")
@Composable
fun AuthScreenInitialPreview() {
    AuthUiState.Initial.Show({}, LocalContext.current, {})
}