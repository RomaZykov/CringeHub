package com.example.domain.repositories.auth

import com.google.firebase.auth.AuthCredential

interface AuthRepository {

    suspend fun signInWithGoogle(firebaseCredential: AuthCredential, onResult: (Throwable?) -> Unit)

    suspend fun signOut()
}