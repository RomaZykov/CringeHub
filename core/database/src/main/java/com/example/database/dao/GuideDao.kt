package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.database.entities.GuideLocal

@Dao
interface GuideDao {

//    @Query("SELECT * FROM numbers_table ORDER BY date DESC")
//    fun allNumbers(): List<NumberCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(guide: GuideLocal)

    @Delete
    fun delete(guide: GuideLocal)

//    @Query("SELECT * FROM numbers_table WHERE number = :number")
//    fun number(number: String): NumberCache?
}