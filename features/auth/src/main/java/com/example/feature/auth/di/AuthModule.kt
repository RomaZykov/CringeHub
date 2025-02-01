package com.example.feature.auth.di

import com.example.common.core.FlowWrapper
import com.example.feature.auth.AuthUiState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Binds
    fun provideFlowWrapper(): FlowWrapper<AuthUiState> = FlowWrapper.Base(AuthUiState.Initial)
}