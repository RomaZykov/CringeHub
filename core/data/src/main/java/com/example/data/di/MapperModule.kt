package com.example.data.di

import com.example.data.core.GuideMapperFactory
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
    abstract fun bindsGuideMapper(impl: GuideMapperFactory.GuideGuideMapper.Base): GuideMapperFactory.GuideGuideMapper

    @Binds
    @Singleton
    abstract fun bindsAdminMapper(impl: GuideMapperFactory.AdminUserGuideMapper.Base): GuideMapperFactory.AdminUserGuideMapper

    @Binds
    @Singleton
    abstract fun bindsUserMapper(impl: GuideMapperFactory.UserGuideMapper.Base): GuideMapperFactory.UserGuideMapper
}