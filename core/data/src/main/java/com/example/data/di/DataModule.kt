package com.example.data.di

import com.example.common.core.HandleError
import com.example.network.exceptions.HandleNetworkException
import com.example.data.impl.auth.AuthGoogleRepositoryImpl
import com.example.domain.repositories.auth.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(
        authRepository: AuthGoogleRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsNetworkErrorHandling(
        handleError: HandleNetworkException
    ): HandleError
}