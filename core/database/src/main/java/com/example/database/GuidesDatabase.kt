package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.GuideDao
import com.example.database.entities.GuideEntity

@Database(entities = [GuideEntity::class], version = 1, exportSchema = false)
abstract class GuidesDatabase : RoomDatabase() {

    abstract fun getGuideDao(): GuideDao
}