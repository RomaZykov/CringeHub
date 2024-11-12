package com.example.data.impl.auth

import androidx.credentials.GetCredentialRequest
import com.example.domain.repositories.auth.AuthFirebaseRepository
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//private const val BASE_URL = BuildConfig.BACKEND_URL

// TODO() - Inject dependencies
class AuthFirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthFirebaseRepository {

    override suspend fun oneTapSignInWithGoogle() {
        val credential = ""
        val signInWithGoogleOption: GetSignInWithGoogleOption =
            GetSignInWithGoogleOption.Builder("")
//            .setNonce(<nonce string to use when generating a Google ID token>)
                .build()
        val request: GetCredentialRequest = Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential,
        onResult: (Throwable?) -> Unit
    ) {
        auth.signInWithCredential(googleCredential).addOnCompleteListener {
            val isNewUser = it.result.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                addUserToFirestore()
            }
            onResult(it.exception)
        }
    }

    private fun addUserToFirestore() {

    }
}