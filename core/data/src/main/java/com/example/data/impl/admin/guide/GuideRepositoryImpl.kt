package com.example.data.impl.admin.guide

import com.example.data.SyncScheduler
import com.example.data.core.GuideMapperFactory
import com.example.data.model.GuideData
import com.example.database.core.admin.GuideLocalDataSource
import com.example.domain.model.GuideDomain
import com.example.domain.repositories.admin.guide.GuideRepository
import com.example.network.core.admin.GuideNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GuideRepositoryImpl @Inject constructor(
    private val networkDataSource: GuideNetworkDataSource,
    private val localDataSource: GuideLocalDataSource,
    private val guideMapperFactory: GuideMapperFactory,
    private val syncScheduler: SyncScheduler
) : GuideRepository.Admin {

    override suspend fun syncWithNetwork(): Boolean {
        return syncScheduler.sync(
            fetchFromNetwork = {
                val networkGuides = networkDataSource.allGuides().first()
                networkGuides.requireNoNulls()
            },

            fetchFromLocal = {
                val localGuides = localDataSource.fetchAllGuides().first()
                localGuides
            },

            updateLocalSource = { networkGuides, guidesToDelete ->
                val localGuides = networkGuides.map {
                    guideMapperFactory.mapToEntity(it)
                }
                guidesToDelete.forEach {
                    localDataSource.deleteGuide(it.id)
                }
                localDataSource.upsertGuides(localGuides)
            }
        )
    }

    override fun fetchDraftGuides(): Flow<List<GuideDomain>> = localDataSource.fetchDraftGuides()
        .map { localGuides ->
            localGuides.map {
                guideMapperFactory.mapToDomain(it)
            }
        }

    override suspend fun updateGuide(guideId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveGuideAsDraft(
        title: String,
        content: String
    ) {
        // При нажатии на кнопку назад -> показать диалоговое окно, если title или content не пустой
        // сохранить изменения в качестве черновика: сохранить / нет
        val uid = UUID.randomUUID().toString()
        val latestModified = System.currentTimeMillis()
        val guideData = GuideData(
            id = uid,
            title = title,
            content = content,
            latestModified = latestModified,
            isDraft = true,
            isFree = false,
            images = emptyList()
        )
        localDataSource.saveGuideAsDraft(uid, title, content, latestModified)

        val guideNetwork = guideMapperFactory.mapToNetwork(guideData)
        syncScheduler.scheduleUploadGuideWork(guideNetwork)
    }

    override suspend fun deleteGuide(guideId: String) {
        localDataSource.deleteGuide(guideId)
        val networkGuide = networkDataSource.getGuide(guideId).first()
        syncScheduler.scheduleUploadGuideWork(networkGuide!!)
    }

    override suspend fun publishGuide() {
        TODO("Not yet implemented")
    }

    override fun fetchPublishedGuides(): Flow<List<GuideDomain>> = localDataSource.fetchAllGuides()
        .map { localGuides ->
            localGuides.map {
                guideMapperFactory.mapToDomain(it)
            }
        }
}