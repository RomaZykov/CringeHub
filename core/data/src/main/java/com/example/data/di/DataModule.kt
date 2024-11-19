package com.example.data.di

import com.example.data.impl.auth.AuthFirebaseRepositoryImpl
import com.example.domain.repositories.auth.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsAuthRepository(
        authRepository: AuthFirebaseRepositoryImpl
    ) : AuthRepository
}