package com.example.data

import com.example.database.entities.GuideEntity
import com.example.network.model.GuideNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

interface SyncScheduler : Syncable {

    override suspend fun syncSuccessful(
        fetchFromNetwork: suspend () -> List<GuideNetwork>,
        fetchFromLocal: suspend () -> List<GuideEntity>,
        updateLocalSource: suspend (networkGuides: List<GuideNetwork>, staleLocalGuides: List<GuideEntity>) -> Unit
    ): Boolean {
        return super.syncSuccessful(fetchFromNetwork, fetchFromLocal, updateLocalSource)
    }

    suspend fun scheduleUploadGuideWork(guide: GuideNetwork, deleteRequest: Boolean = false)

    suspend fun scheduleSyncLatestWork()
}

// TODO: Update with deleting
interface Syncable {
    suspend fun syncSuccessful(
        fetchFromNetwork: suspend () -> List<GuideNetwork>,

        fetchFromLocal: suspend () -> List<GuideEntity>,

        updateLocalSource: suspend (
            networkGuides: List<GuideNetwork>,
            staleLocalGuides: List<GuideEntity>
        ) -> Unit
    ): Boolean {
        val networkGuides = mutableListOf<GuideNetwork>()
        val localGuides = mutableListOf<GuideEntity>()
        return try {
            coroutineScope {
                launch {
                    networkGuides.addAll(
                        fetchFromNetwork.invoke()
                    )
                }

                launch {
                    localGuides.addAll(
                        fetchFromLocal.invoke()
                    )
                }
            }.join()

            updateLocalSource(
                networkGuides,
                localGuides
            )
            true
        } catch (e: IOException) {
            false
        }
    }
}
