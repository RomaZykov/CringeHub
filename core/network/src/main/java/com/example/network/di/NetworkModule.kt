package com.example.network.di

import com.example.network.core.UserNetworkDataSource
import com.example.network.firebase.FirebaseDataSource
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
    abstract fun providesUserNetworkDataSource(impl: FirebaseDataSource): UserNetworkDataSource
}