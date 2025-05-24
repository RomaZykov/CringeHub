package com.example.workmanager.di

import com.example.data.SyncScheduler
import com.example.workmanager.SyncSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SyncModule {
    @Binds
    abstract fun bindsSyncScheduler(impl: SyncSchedulerImpl): SyncScheduler
}