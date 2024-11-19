package com.example.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data object AuthUiState {

    @Composable
    fun GoogleSignInButton(onSignInClick: () -> Unit) {
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
                text = stringResource(R.string.google_sign_in),
                color = colorResource(R.color.black_google_font)
            )
        }
    }
}

@Preview(showBackground = true, locale = "en")
@Composable
fun GoogleSignInButtonPreview() {
    AuthUiState.GoogleSignInButton {  }
}