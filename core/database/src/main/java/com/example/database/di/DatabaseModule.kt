package com.example.database.di

import com.example.database.core.admin.GuideLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatabaseModule {
    @Binds
    abstract fun bindGuideLocalDataSource(impl: GuideLocalDataSource.Base): GuideLocalDataSource
}
