package com.example.domain.repositories

import android.content.Context
import com.example.domain.model.AdminUserDomain
import com.example.domain.model.UserDomain

interface AuthRepository {

    // TODO: Add loggedIn
//    val isLoggedIn: Result<Boolean>

    interface Admin : AuthRepository {
        suspend fun signInWithEmail(email: String, password: String): Result<AdminUserDomain>
    }

    interface Client : AuthRepository {
        // Use specific activityContext for credential manager from Composable AuthScreen
        // does not work with @ApplicationContext from Hilt
        suspend fun signInWithGoogle(activityContext: Context): Result<UserDomain>
    }

    suspend fun signOut()
}
