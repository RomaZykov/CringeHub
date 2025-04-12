package com.example.adminhome.di

import com.example.adminhome.navigation.AdminHomeRouteBuilder
import com.example.adminhome.navigation.BaseAdminHomeRouteProvider
import com.example.adminnavigation.AdminHomeRouteProvider
import com.example.adminnavigation.RouteBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.multibindings.IntoSet

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    @ViewModelScoped
    fun provideAdminHomeRouteProvider(): AdminHomeRouteProvider = BaseAdminHomeRouteProvider()

    @Provides
    @IntoSet
    fun provideAdminHomeRouteBuilder(): RouteBuilder = AdminHomeRouteBuilder()
}