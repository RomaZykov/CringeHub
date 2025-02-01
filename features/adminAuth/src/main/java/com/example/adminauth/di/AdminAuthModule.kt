package com.example.adminauth.di

import com.example.adminauth.AdminAuthUiState
import com.example.common.core.FlowWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AdminAuthModule {
    @Provides
    fun provideFlowWrapper(): FlowWrapper<AdminAuthUiState> = FlowWrapper.Base(AdminAuthUiState.Initial)
}