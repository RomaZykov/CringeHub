package com.example.adminguidecreation.di

import com.example.adminguidecreation.GuideCreationUiState
import com.example.common.core.FlowWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GuideCreationModule {

    @Provides
    @Singleton
    fun provideFlowWrapper(): FlowWrapper<GuideCreationUiState> = FlowWrapper.Base(GuideCreationUiState.Initial)
}