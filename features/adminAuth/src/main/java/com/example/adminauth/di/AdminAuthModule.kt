package com.example.adminauth.di

import com.example.adminauth.navigation.AdminAuthRouteBuilder
import com.example.adminauth.navigation.BaseAdminAuthRouteProvider
import com.example.adminnavigation.AdminAuthRouteProvider
import com.example.adminnavigation.RouteBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.multibindings.IntoSet

@Module
@InstallIn(ViewModelComponent::class)
object AdminAuthModule {

    @Provides
    @ViewModelScoped
    fun provideAdminAuthRouteProvider(): AdminAuthRouteProvider = BaseAdminAuthRouteProvider()

    @Provides
    @IntoSet
    fun provideAdminAuthRouteBuilder(): RouteBuilder = AdminAuthRouteBuilder()
}