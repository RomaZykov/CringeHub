package com.example.domain.repositories.auth

interface AuthRepository {

    suspend fun signIn()

    suspend fun logOut()
}