package com.example.adminguidecreation.di

import com.example.adminguidecreation.navigation.BaseDraftRouteProvider
import com.example.adminguidecreation.navigation.BaseGuideCreationRouteProvider
import com.example.adminguidecreation.navigation.GuideCreationRouteBuilder
import com.example.adminnavigation.DraftRouteProvider
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
    fun provideGuideCreationRouteProvider(): GuideCreationRouteProvider = BaseGuideCreationRouteProvider()

    @Provides
    fun provideDraftRouteProvider(): DraftRouteProvider = BaseDraftRouteProvider()

    @Provides
    @IntoSet
    fun provideGuideCreationRouteBuilder(): RouteBuilder = GuideCreationRouteBuilder()
}