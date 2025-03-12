package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.GuideDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {
    @Provides
    @Singleton
    @Named("test_guide_db")
    fun provideTestGuideDatabase(@ApplicationContext context: Context): GuideDatabase {
        return Room.inMemoryDatabaseBuilder(context, GuideDatabase::class.java)
            .build()
    }
}