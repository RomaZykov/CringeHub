package com.example.adminhome.di

import com.example.adminhome.core.FlowWrapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindsFlowWrapper(flowWrapper: FlowWrapper.Base): FlowWrapper
}