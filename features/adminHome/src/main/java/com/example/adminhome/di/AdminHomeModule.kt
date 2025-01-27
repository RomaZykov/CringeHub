package com.example.adminhome.di

import com.example.adminhome.AdminHomeUiState
import com.example.common.core.FlowWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFlowWrapper(): FlowWrapper<AdminHomeUiState> = FlowWrapper.Base(AdminHomeUiState.Initial)
}