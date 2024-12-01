package com.example.domain.repositories.auth

import android.content.Context

interface AuthRepository {

    // Use specific activityContext for credential manager from Composable AuthScreen
    // does not work with @ApplicationContext from Hilt
    suspend fun signInWithGoogle(activityContext: Context)

    suspend fun signOut()
}
