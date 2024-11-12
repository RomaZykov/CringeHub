package com.example.domain.repositories.auth

import com.google.firebase.auth.AuthCredential

interface AuthFirebaseRepository {

    suspend fun oneTapSignInWithGoogle()

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential, onResult: (Throwable?) -> Unit)
}