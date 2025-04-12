package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.GuidesDatabase
import com.example.database.dao.GuideDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideGuideDatabase(@ApplicationContext context: Context): GuidesDatabase {
        return Room.databaseBuilder(
            context,
            GuidesDatabase::class.java,
            "guide_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGuideDao(database: GuidesDatabase): GuideDao {
        return database.getGuideDao()
    }
}