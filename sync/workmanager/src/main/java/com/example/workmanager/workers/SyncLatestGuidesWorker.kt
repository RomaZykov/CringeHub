package com.example.workmanager.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.repositories.admin.guide.GuideRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

/**
 * Sync between network and local data sources when start app.
 * For example:
 * 1) If there is no local data (user cleared app data)
 * -> fetch from network to syncSuccessful with local;
 * 2) If there is the latest data from local -> update network data.
 */
@HiltWorker
class SyncLatestGuidesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val guideRepository: GuideRepository,
    private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
//        val userSignedIn = authRepository.isLoggedIn.first()

        val syncSuccessful = awaitAll(
            async { guideRepository.syncWithNetwork() }
        ).all { it }

        if (syncSuccessful) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        const val SYNC_GUIDES_WORK_NAME = "sync_guides"
    }
}
