package com.example.auth_domain.repository

interface AuthRepository {

    suspend fun signIn()

    suspend fun logOut()
}