package com.example.data.impl.auth

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.example.data.model.UserData
import com.example.domain.model.User
import com.example.domain.repositories.auth.AuthRepository
import com.example.network.core.UserNetworkDataSource
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthGoogleRepositoryImpl @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun signInWithGoogle(activityContext: Context): Result<User> =
        withContext(dispatcher) {
            try {
                val credentialResult =
                    credentialManager.getCredential(activityContext, credentialRequest)
                handleFirebaseSignIn(credentialResult)

                val rawUser = userNetworkDataSource.getUser()
                val user = UserData(
                    rawUser.id.orEmpty(),
                    rawUser.userName.orEmpty(),
                    isLoggedIn = true
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
        val googleIdTokenCredential =
            GoogleIdTokenCredential.createFrom(result.credential.data)
        val idToken = googleIdTokenCredential.idToken

        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        userNetworkDataSource.signInUserWithFirebaseCredential(
            firebaseCredential
        )
    }
}
