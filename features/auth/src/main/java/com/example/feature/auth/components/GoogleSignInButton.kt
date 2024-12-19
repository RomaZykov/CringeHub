package com.example.feature.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cringehub.theme.CringeHubTheme
import com.example.feature.auth.R

@Composable
fun GoogleSignInButton(onSignInClick: () -> Unit) {
    Button(
        onClick = onSignInClick,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(40.dp)
            .padding(horizontal = 32.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CringeHubTheme.colorScheme.background,
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
                text = stringResource(R.string.google_sign_in),
                color = CringeHubTheme.colorScheme.onBackground
            )
        }
    }
}