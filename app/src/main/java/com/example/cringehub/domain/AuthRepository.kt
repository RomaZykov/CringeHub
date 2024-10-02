package com.example.cringehub.domain

interface AuthRepository {

    suspend fun signIn()

    suspend fun logOut()
}