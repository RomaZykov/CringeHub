package com.example.data.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.common.BuildConfig
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideSignInGoogleOption() =
        GetSignInWithGoogleOption.Builder(BuildConfig.SERVER_CLIENT_ID)
            .build()

    @Provides
    @Singleton
    fun provideCredentialRequest(options: GetSignInWithGoogleOption) =
        GetCredentialRequest.Builder()
            .addCredentialOption(options)
            .build()

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context) =
        CredentialManager.create(context)
}
