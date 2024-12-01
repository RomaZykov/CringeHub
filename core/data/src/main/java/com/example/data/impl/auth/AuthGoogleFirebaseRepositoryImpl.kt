package com.example.data.impl.auth

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.example.domain.repositories.auth.AuthRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class AuthGoogleFirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest,
    private val db: FirebaseFirestore
) : AuthRepository {

    override suspend fun signInWithGoogle(activityContext: Context) {
        CoroutineScope(coroutineContext).launch {
            try {
                val credentialResult =
                    credentialManager.getCredential(activityContext, credentialRequest)
                handleFirebaseSignIn(credentialResult)
            } catch (e: GetCredentialException) {
                // TODO - Implement correct realization
                println(e)
            }
        }
    }

    override suspend fun signOut() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        auth.signOut()
    }

    private fun handleFirebaseSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        val firebaseCredential =
                            GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener {
                                val isNewUser =
                                    it.result.additionalUserInfo?.isNewUser ?: false
                                if (isNewUser) {
                                    addUserToFirestore()
                                }
                            }
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                } else {
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

    private fun addUserToFirestore() {

    }
}
