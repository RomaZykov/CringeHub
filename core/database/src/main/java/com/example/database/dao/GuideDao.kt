package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.database.entities.GuideEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GuideDao {

    @Upsert
    fun upsertGuides(entities: List<GuideEntity>)

    @Query("SELECT * FROM guides_table")
    fun allGuides(): Flow<List<GuideEntity>>

    @Query("SELECT * FROM guides_table WHERE id = :guideId")
    fun getGuide(guideId: Long): Flow<GuideEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(guide: GuideEntity)

    @Query("delete from guides_table where id = :guideId")
    fun delete(guideId: Long)
}