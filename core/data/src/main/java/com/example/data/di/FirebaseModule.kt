package com.example.data.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirestore() = Firebase.firestore

    @Provides
    fun provideCredentialManager(@ApplicationContext context: Context) =
        CredentialManager.create(context)

    @Provides
    fun provideSignInGoogleOption(buildConfig: BuildConfig) =
        GetSignInWithGoogleOption.Builder(buildConfig)
            .build()
}