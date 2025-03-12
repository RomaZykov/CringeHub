package com.example.data.di

import com.example.common.core.HandleError
import com.example.data.impl.admin.auth.AuthAdminEmailRepositoryImpl
import com.example.data.impl.client.auth.AuthGoogleRepositoryImpl
import com.example.domain.repositories.AuthRepository
import com.example.network.exceptions.HandleNetworkException
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
    abstract fun bindsGoogleAuthRepository(
        googleAuthRepository: AuthGoogleRepositoryImpl
    ): AuthRepository.GoogleAuthRepository

    @Binds
    @Singleton
    abstract fun bindsAdminAuthRepository(
        adminAuthRepository: AuthAdminEmailRepositoryImpl
    ): AuthRepository.AdminAuthRepository

    @Binds
    @Singleton
    abstract fun bindsNetworkErrorHandling(
        handleError: HandleNetworkException
    ): HandleError
}