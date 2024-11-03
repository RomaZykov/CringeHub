package com.example.data.impl.auth

import com.example.domain.repositories.auth.AuthRepository
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

//private const val BASE_URL = BuildConfig.BACKEND_URL

class GoogleRepositoryImpl : AuthRepository {

    override suspend fun signIn() {
        TODO("Not yet implemented")
    }

    override suspend fun logOut() {
        Firebase.auth.signOut()
    }
}