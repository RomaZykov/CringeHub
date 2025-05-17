package com.example.data.di

import com.example.domain.repositories.AuthRepository
import com.example.domain.repositories.admin.guide.GuideRepository
import com.example.test.repository.FakeAdminAuthRepository
import com.example.test.repository.FakeAdminGuideRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DataModule::class])
object TestModule {

    @Provides
    @Singleton
    fun provideFakeAdminGuideRepo(): GuideRepository.Admin = FakeAdminGuideRepository()

    @Provides
    @Singleton
    fun provideFakeAdminAuthRepo(): AuthRepository.Admin = FakeAdminAuthRepository()

    @Provides
    @Singleton
    fun provideFakeClientAuthRepo(): AuthRepository.Client = TODO()
}