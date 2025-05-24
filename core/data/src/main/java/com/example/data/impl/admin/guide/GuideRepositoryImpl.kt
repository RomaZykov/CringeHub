package com.example.data.impl.admin.guide

import com.example.data.SyncScheduler
import com.example.data.core.mappers.GuideMapperFactory
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
        return syncScheduler.syncSuccessful(
            fetchFromNetwork = {
                networkDataSource.allGuides().first().requireNoNulls()
            },

            fetchFromLocal = {
                localDataSource.fetchAllGuides().first()
            },

            updateLocalSource = { networkGuides, localGuides ->
                val updatedLocalGuides = networkGuides.map {
                    guideMapperFactory.mapToEntity(it)
                }
                val guidesToDelete = localGuides.filter { localGuide ->
                    val similarGuides = networkGuides.filter { networkGuide ->
                        localGuide.id == networkGuide.id
                    }.map { it.id }
                    !similarGuides.contains(localGuide.id)
                }
                guidesToDelete.forEach {
                    localDataSource.deleteGuide(it.id)
                }
                localDataSource.upsertGuides(updatedLocalGuides)
            }
        )
    }

    override suspend fun upsertGuide(
        id: String,
        title: String,
        content: String
    ) {
        // TODO: Don`t generate id if exist (e.g. when dialog is shown but you decided to cancel)
        val latestModified = System.currentTimeMillis()
        val guideData = GuideData(
            id = id.ifBlank { UUID.randomUUID().toString() },
            title = title,
            content = content,
            latestModified = latestModified,
            isDraft = true,
            isFree = false,
            images = emptyList()
        )
        localDataSource.upsertGuide(guideMapperFactory.mapToEntity(guideData))

        val guideNetwork = guideMapperFactory.mapToNetwork(guideData)
        syncScheduler.scheduleUploadGuideWork(guideNetwork)
    }

    override suspend fun deleteGuide(guideId: String) {
        localDataSource.deleteGuide(guideId)
        syncScheduler.scheduleUploadGuideWork(
            guideMapperFactory.mapToNetwork(GuideData(guideId)),
            deleteRequest = true
        )
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

    override fun fetchDraftGuides(): Flow<List<GuideDomain>> = localDataSource.fetchDraftGuides()
        .map { localGuides ->
            localGuides.map {
                guideMapperFactory.mapToDomain(it)
            }
        }
}