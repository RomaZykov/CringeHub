package com.example.feature.auth.di

import com.example.feature.auth.core.FlowWrapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun provideFlowWrapper(flowWrapper: FlowWrapper.Base): FlowWrapper
}