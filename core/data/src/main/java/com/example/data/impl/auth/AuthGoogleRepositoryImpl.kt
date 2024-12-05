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
import com.example.data.model.UserData
import com.example.domain.model.User
import com.example.domain.repositories.auth.AuthRepository
import com.example.network.core.UserNetworkDataSource
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthGoogleRepositoryImpl @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest,
    // TODO - replace from di
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    override suspend fun signInWithGoogle(activityContext: Context): Result<User> =
        withContext(dispatcher) {
            try {
                val credentialResult =
                    credentialManager.getCredential(activityContext, credentialRequest)
                handleFirebaseSignIn(credentialResult)

                val rawUser = userNetworkDataSource.getUser()
                val user =
                    UserData(
                        rawUser.id.orEmpty(),
                        rawUser.userName.orEmpty(),
                        isAuthorize = true
                    )
                Result.success(user.mappedValue())
            } catch (e: GetCredentialException) {
                Result.failure(e)
            }
        }

    override suspend fun signOut() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        userNetworkDataSource.signOutUser()
    }

    private suspend fun handleFirebaseSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken

                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        userNetworkDataSource.signInUserWithFirebaseCredential(
                            firebaseCredential
                        )
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
}
