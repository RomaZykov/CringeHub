package com.example.auth

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    signIn: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.state().collectAsState()

    uiState

//    val launcher = rememberLauncherForActivityResult(StartIntentSenderForResult()) { result ->
//        if (result.resultCode == RESULT_OK) {
//            try {
//                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
//                val googleIdToken = credentials.googleIdToken
//                val googleCredentials = getCredential(googleIdToken, null)
//                viewModel.signInWithGoogle(googleCredentials)
//            } catch (it: ApiException) {
//                println(it)
//            }
//        }
//    }
}

//@Preview(showBackground = true)
//@Composable
//fun AuthScreenPreview() {
//}