package com.example.network.di

import com.example.network.core.UserNetworkDataSource
import com.example.network.core.admin.AdminUserNetworkDataSource
import com.example.network.core.admin.GuideNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindUserNetworkDataSource(impl: UserNetworkDataSource.Base): UserNetworkDataSource

    // Admin
    @Binds
    @Singleton
    abstract fun bindAdminUserNetworkDataSource(impl: AdminUserNetworkDataSource.Base): AdminUserNetworkDataSource

    @Binds
    @Singleton
    abstract fun bindGuideNetworkDataSource(impl: GuideNetworkDataSource.Base): GuideNetworkDataSource
}