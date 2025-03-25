package com.example.data.di

import com.example.data.MapperFactory
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun bindsMapper(impl: MapperFactory.Base): MapperFactory
}