package com.example.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.database.entities.GuideEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GuideDao {

    @Upsert
    suspend fun upsertGuides(entities: List<GuideEntity>)

    @Query("SELECT * FROM guides_table")
    fun allGuides(): Flow<List<GuideEntity>>

    @Query("SELECT * FROM guides_table WHERE id = :guideId")
    fun getGuide(guideId: String): Flow<GuideEntity>

    @Upsert
    suspend fun upsert(guide: GuideEntity)

    @Query("delete from guides_table where id = :guideId")
    suspend fun delete(guideId: String)
}