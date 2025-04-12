package com.example.data.di

import com.example.common.core.HandleError
import com.example.data.impl.admin.auth.AuthAdminEmailRepositoryImpl
import com.example.data.impl.admin.guide.GuideRepositoryImpl
import com.example.data.impl.client.auth.AuthGoogleRepositoryImpl
import com.example.domain.repositories.AuthRepository
import com.example.domain.repositories.admin.guide.GuideRepository
import com.example.network.exceptions.HandleNetworkException
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsGoogleAuthRepository(
        googleAuthRepository: AuthGoogleRepositoryImpl
    ): AuthRepository.GoogleAuthRepository

    @Binds
    abstract fun bindsAdminAuthRepository(
        adminAuthRepository: AuthAdminEmailRepositoryImpl
    ): AuthRepository.AdminAuthRepository

    @Binds
    abstract fun bindsGuideRepository(
        guideRepositoryImpl: GuideRepositoryImpl
    ): GuideRepository.Admin

    @Binds
    abstract fun bindsNetworkErrorHandling(
        handleError: HandleNetworkException
    ): HandleError
}