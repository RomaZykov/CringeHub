package com.example.data.di

import com.example.data.MapperFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun bindsGuideMapper(impl: MapperFactory.GuideMapper.Base): MapperFactory.GuideMapper

    @Binds
    @Singleton
    abstract fun bindsAdminMapper(impl: MapperFactory.AdminMapper.Base): MapperFactory.AdminMapper

    @Binds
    @Singleton
    abstract fun bindsUserMapper(impl: MapperFactory.UserMapper.Base): MapperFactory.UserMapper
}