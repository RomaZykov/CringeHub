package com.example.workmanager.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.network.core.admin.GuideNetworkDataSource
import com.example.network.model.GuideNetwork
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

/**
 * Upload changes when operation has executed to not store a stale data in network.
 * Source of true: local data source.
 * Operations can be executed in queue, e.g. user has created a new guide
 * then deleted another then updated third guide.
 */
@HiltWorker
class UploadGuideChangesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val guideNetworkDataSource: GuideNetworkDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val guide = inputData.keyValueMap[GUIDE_KEY] as GuideNetwork
        val uploadChangesSuccessful = awaitAll(
            async { guideNetworkDataSource.saveGuideAsDraft(guide) },
            async { guide.id?.let { guideNetworkDataSource.deleteGuide(it) } }
        )

        if (uploadChangesSuccessful.filter { true }.size == ONLY_ONE_OPERATION_TRUE) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        private const val ONLY_ONE_OPERATION_TRUE = 1
        const val GUIDE_KEY = "guide_key"
    }
}
