package com.example.adminauth.di

import com.example.adminauth.core.FlowWrapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AdminAuthModule {
    @Binds
    abstract fun bindsFlowWrapper(flowWrapper: FlowWrapper.Base): FlowWrapper
}