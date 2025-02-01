package com.example.adminguidecreation.di

import com.example.adminguidecreation.GuideCreationUiState
import com.example.common.core.FlowWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object GuideCreationModule {

    @Provides
    fun provideFlowWrapper(): FlowWrapper<GuideCreationUiState> = FlowWrapper.Base(GuideCreationUiState.Initial)
}