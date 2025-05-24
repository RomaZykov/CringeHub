package com.example.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.database.dao.GuideDao
import org.junit.After
import org.junit.Before
import java.io.IOException

abstract class DatabaseTest {
    private lateinit var db: GuidesDatabase
    lateinit var guideDao: GuideDao

    @Before
    fun createDb() {
        db = run {
            val context = ApplicationProvider.getApplicationContext<Context>()
            Room.inMemoryDatabaseBuilder(
                context,
                GuidesDatabase::class.java,
            ).build()
        }
        guideDao = db.getGuideDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}