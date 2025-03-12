package com.example.database.core.admin

import com.example.database.dao.GuideDao
import com.example.database.entities.GuideLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GuideLocalDataSource {

    fun getAllGuides(): Flow<List<GuideLocal>>

    fun getGuide(id: Int): Flow<GuideLocal>

    suspend fun saveGuideAsDraft(guide: GuideLocal)

    class Base @Inject constructor(private val dao: GuideDao) : GuideLocalDataSource {
        override fun getAllGuides(): Flow<List<GuideLocal>> {
            TODO("Not yet implemented")
        }

        override fun getGuide(id: Int): Flow<GuideLocal> {
            TODO("Not yet implemented")
        }

        override suspend fun saveGuideAsDraft(guide: GuideLocal) {
            TODO("Not yet implemented")
        }
    }
}