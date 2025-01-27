package com.example.feature.auth.di

import com.example.common.core.FlowWrapper
import com.example.feature.auth.AuthUiState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Binds
    @Singleton
    fun provideFlowWrapper(): FlowWrapper<AuthUiState> = FlowWrapper.Base(AuthUiState.Initial)
}