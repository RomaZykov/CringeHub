package com.example.feature.auth

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cringehub.theme.CringeHubTheme
import com.example.cringehub.theme.GoogleBackgroundGray
import com.example.cringehub.theme.PrimaryYellow

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
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                CringeHubTheme.colorScheme.primary,
                                CringeHubTheme.colorScheme.secondary
                            )
                        )
                    )
            ) {
                Spacer(modifier = Modifier.weight(0.1f))
                WelcomeLogoAndTitle()
                Spacer(modifier = Modifier.padding(CringeHubTheme.dimensions.spaceSmall))
                WelcomeView()
                Spacer(modifier = Modifier.padding(CringeHubTheme.dimensions.spaceMedium))
                WelcomeText()
                Spacer(modifier = Modifier.weight(0.1f))
                GoogleSignInButton {
                    onSignInClick(authScreenContext)
                }
                Spacer(modifier = Modifier.weight(0.15f))
            }
        }

        @Composable
        private fun GoogleSignInButton(onSignInClick: () -> Unit) {
            Button(
                onClick = onSignInClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(40.dp)
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoogleBackgroundGray,
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    modifier = Modifier.padding(12.dp, end = 10.dp),
                    contentDescription = null
                )
                ProvideTextStyle(value = CringeHubTheme.typography.googleButton) {
                    Text(
                        modifier = Modifier.padding(end = 12.dp),
                        text = stringResource(R.string.google_sign_in)
                    )
                }
            }
        }

        @Composable
        private fun WelcomeLogoAndTitle() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CringeHubTheme.dimensions.spaceSmall),
                horizontalArrangement = Arrangement.Center
            ) {
                val mainLogo = ImageVector.vectorResource(id = R.drawable.main_logo)
                Image(mainLogo, "")

                Spacer(modifier = Modifier.padding(CringeHubTheme.dimensions.spaceExtraSmall))

                val mainTitle = ImageVector.vectorResource(id = R.drawable.main_title)
                Image(mainTitle, "")
            }
        }

        @Composable
        private fun WelcomeView() {
            Image(
                modifier = Modifier.padding(horizontal = CringeHubTheme.dimensions.spaceSmall),
                painter = painterResource(R.drawable.login_view),
                contentDescription = ""
            )
        }

        @Composable
        private fun WelcomeText() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ProvideTextStyle(value = CringeHubTheme.typography.title) {
                    Text(
                        buildAnnotatedString {
                            append(stringResource(R.string.welcome_main_title_part_1))
                            withStyle(
                                style = SpanStyle(
                                    color = PrimaryYellow
                                )
                            ) {
                                append(stringResource(R.string.welcome_main_title_part_2))
                            }
                            append(stringResource(R.string.welcome_main_title_part_3))
                        },
                        modifier = Modifier.align(alignment = Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
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

    // TODO: How to handle Error correctly?
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

@Preview(showSystemUi = true, locale = "en")
@Composable
fun AuthScreenInitialPreview() {
    AuthUiState.Initial.Show({}, LocalContext.current, {})
}