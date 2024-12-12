package com.example.data.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.common.BuildConfig
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

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
