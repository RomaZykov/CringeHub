package com.example.adminhome.di

import com.example.adminhome.AdminHomeUiState
import com.example.common.core.FlowWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun provideFlowWrapper(): FlowWrapper<AdminHomeUiState> = FlowWrapper.Base(AdminHomeUiState.Initial)
}