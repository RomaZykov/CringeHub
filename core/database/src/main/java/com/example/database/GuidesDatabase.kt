package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.GuideDao
import com.example.database.entities.GuideLocal

@Database(entities = [GuideLocal::class], version = 1, exportSchema = false)
abstract class GuideDatabase : RoomDatabase() {

    abstract fun getGuideDao(): GuideDao
}