package com.example.auth

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    signIn: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.state().collectAsState()

    uiState.GoogleSignInButton()

    val launcher = rememberLauncherForActivityResult(StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = getCredential(googleIdToken, null)
                viewModel.signInWithGoogleViaFirebase(googleCredentials)
            } catch (it: ApiException) {
                Log.d("GoogleAuth", e.toString())
                println(it)
            }
        }
    }

//    @Composable
//    fun rememberFirebaseAuthLauncher(
//        onAuthComplete: (AuthResult) -> Unit,
//        onAuthError: (ApiException) -> Unit
//    ): ManagedActivityResultLauncher<Intent, ActivityResult> {
//        val scope = rememberCoroutineScope()
//        return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            try {
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d("GoogleAuth", "account $account")
//                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
//                scope.launch {
//                    val authResult = Firebase.auth.signInWithCredential(credential).await()
//
//                    onAuthComplete(authResult)
//
//                }
//            } catch (e: ApiException) {
//                Log.d("GoogleAuth", e.toString())
//                onAuthError(e)
//            }
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {

}