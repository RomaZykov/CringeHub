package com.example.network.core

import com.example.network.model.UserNetwork
import com.google.firebase.auth.AuthCredential

interface UserNetworkDataSource {

    suspend fun signInUserWithFirebaseCredential(firebaseCredential: AuthCredential)

    suspend fun signOutUser()

    suspend fun getUser(): UserNetwork
}