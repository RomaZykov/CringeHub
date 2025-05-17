package com.example.data.di

import com.example.data.core.mappers.AdminUserMapperFactory
import com.example.data.core.mappers.GuideMapperFactory
import com.example.data.core.mappers.UserMapperFactory
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
    abstract fun bindsGuideMapper(impl: GuideMapperFactory.Base): GuideMapperFactory

    @Binds
    @Singleton
    abstract fun bindsAdminMapper(impl: AdminUserMapperFactory.Base): AdminUserMapperFactory

    @Binds
    @Singleton
    abstract fun bindsUserMapper(impl: UserMapperFactory.Base): UserMapperFactory
}