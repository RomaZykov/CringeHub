package com.example.domain.repositories.auth

import android.content.Context
import com.example.domain.model.User

interface AuthRepository {

    // Use specific activityContext for credential manager from Composable AuthScreen
    // does not work with @ApplicationContext from Hilt
    suspend fun signInWithGoogle(activityContext: Context): Result<User>

    suspend fun signOut()
}
