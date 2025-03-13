package com.example.database.di

import com.example.database.core.admin.GuideLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatabaseModule {
    @Binds
    @Singleton
    abstract fun bindUserNetworkDataSource(impl: GuideLocalDataSource.Base): GuideLocalDataSource
}
