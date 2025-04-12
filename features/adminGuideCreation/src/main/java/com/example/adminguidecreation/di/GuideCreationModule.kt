package com.example.adminguidecreation.di

import com.example.adminguidecreation.navigation.BaseGuideCreationCreationRouteProvider
import com.example.adminguidecreation.navigation.GuideCreationRouteBuilder
import com.example.adminnavigation.GuideCreationRouteProvider
import com.example.adminnavigation.RouteBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object GuideCreationModule {
    @Provides
    fun provideGuideCreationRouteProvider(): GuideCreationRouteProvider = BaseGuideCreationCreationRouteProvider()

    @Provides
    @IntoSet
    fun provideGuideCreationRouteBuilder(): RouteBuilder = GuideCreationRouteBuilder()
}