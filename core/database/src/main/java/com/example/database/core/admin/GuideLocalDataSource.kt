package com.example.database.core.admin

import com.example.database.dao.GuideDao
import com.example.database.entities.GuideEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GuideLocalDataSource {

    suspend fun upsertGuides(entities: List<GuideEntity>)

    fun fetchAllGuides(): Flow<List<GuideEntity>>

    fun fetchDraftGuides(): Flow<List<GuideEntity>>

    fun getGuide(id: String): Flow<GuideEntity>

    suspend fun saveGuideAsDraft(uid: String, title: String, content: String, latestModified: Long)

    suspend fun deleteGuide(uid: String)

    class Base @Inject constructor(
        private val dao: GuideDao,
        private val ioDispatcher: CoroutineDispatcher
    ) : GuideLocalDataSource {

        override suspend fun upsertGuides(entities: List<GuideEntity>) {
            withContext(ioDispatcher) {
                dao.upsertGuides(entities)
            }
        }

        override fun fetchAllGuides(): Flow<List<GuideEntity>> = dao.allGuides()

        // TODO: Replace with correct impl
        override fun fetchDraftGuides(): Flow<List<GuideEntity>> = dao.allGuides()

        override fun getGuide(id: String): Flow<GuideEntity> = dao.getGuide(id)

        override suspend fun saveGuideAsDraft(
            uid: String,
            title: String,
            content: String,
            latestModified: Long
        ) {
            withContext(ioDispatcher) {
                val guideAsDraft = GuideEntity(
                    uid,
                    title,
                    content,
                    System.currentTimeMillis(),
                    true
                )
                dao.insert(guideAsDraft)
            }
        }

        override suspend fun deleteGuide(uid: String) {
            TODO("Not yet implemented")
        }
    }
}