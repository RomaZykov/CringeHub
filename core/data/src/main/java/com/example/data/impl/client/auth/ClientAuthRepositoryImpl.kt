package com.example.data.impl.client.auth

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.common.core.HandleError
import com.example.data.core.mappers.UserMapperFactory
import com.example.data.model.UserData
import com.example.domain.model.UserDomain
import com.example.domain.repositories.AuthRepository
import com.example.network.core.UserNetworkDataSource
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClientAuthRepositoryImpl @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest,
    private val handleError: HandleError,
    private val mapper: UserMapperFactory,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository.Client {

    override suspend fun signInWithGoogle(activityContext: Context): Result<UserDomain> =
        withContext(dispatcher) {
            try {
                val credentialResult =
                    credentialManager.getCredential(activityContext, credentialRequest)
                handleFirebaseSignIn(credentialResult)

                val rawUser =
                    userNetworkDataSource.getUser()
                val userData = UserData(
                    rawUser?.id.orEmpty(),
                    rawUser?.userName.orEmpty(),
                    isLoggedIn = true
                )
                Result.success(mapper.mapToDomain(userData))
            } catch (e: Exception) {
                Result.failure(handleError.handle(e))
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
