package com.example.database.core.admin

import com.example.database.dao.GuideDao
import com.example.database.entities.GuideEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GuideLocalDataSource {

    suspend fun upsertGuides(entities: List<GuideEntity>)

    fun fetchAllGuides(): Flow<List<GuideEntity>>

    fun fetchDraftGuides(): Flow<List<GuideEntity>>

    fun fetchPublishedGuides(): Flow<List<GuideEntity>>

    fun getGuide(id: String): Flow<GuideEntity>

    suspend fun upsertGuide(guide: GuideEntity)

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

        override fun fetchDraftGuides(): Flow<List<GuideEntity>> {
            return dao.allGuides().map { guideList ->
                guideList.filter { it.isDraft }
            }
        }

        override fun fetchPublishedGuides(): Flow<List<GuideEntity>> {
            return dao.allGuides().map { guideList ->
                guideList.filter { !it.isDraft }
            }
        }

        override fun getGuide(id: String): Flow<GuideEntity> = dao.getGuide(id)

        override suspend fun upsertGuide(
            guide: GuideEntity
        ) {
            withContext(ioDispatcher) {
                dao.upsert(guide)
            }
        }

        override suspend fun deleteGuide(uid: String) {
            TODO("Not yet implemented")
        }
    }
}