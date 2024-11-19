package com.example.domain.repositories.auth

interface AuthRepository {

    suspend fun signInWithGoogle()

    suspend fun signOut()
}