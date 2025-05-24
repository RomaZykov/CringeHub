package com.example.network.di

import com.example.network.core.UserNetworkDataSource
import com.example.network.core.admin.AdminUserNetworkDataSource
import com.example.network.core.admin.GuideNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {
    @Binds
    abstract fun bindUserNetworkDataSource(impl: UserNetworkDataSource.Base): UserNetworkDataSource

    // Admin
    @Binds
    abstract fun bindAdminUserNetworkDataSource(impl: AdminUserNetworkDataSource.Base): AdminUserNetworkDataSource

    @Binds
    abstract fun bindGuideNetworkDataSource(impl: GuideNetworkDataSource.Base): GuideNetworkDataSource
}