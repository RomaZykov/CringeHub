package com.example.workmanager.workers

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.network.core.admin.GuideNetworkDataSource
import com.example.network.model.GuideNetwork
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
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

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(UPLOAD_NOTIFICATION_ID, createNotification(applicationContext))
    }

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val (guide, deleteRequest) = getMappedWorkData()
        val uploadChangesSuccessful = if (deleteRequest) {
            async {
                guideNetworkDataSource.deleteGuide(guide.id!!)
            }
        } else {
            async { guideNetworkDataSource.upsertGuide(guide) }
        }.await().isSuccess

        if (uploadChangesSuccessful) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    private fun getMappedWorkData(): Pair<GuideNetwork, Boolean> = Pair(
        GuideNetwork(
            id = inputData.getString(GUIDE_ID_KEY),
            title = inputData.getString(GUIDE_TITLE_KEY),
            content = inputData.getString(GUIDE_CONTENT_KEY),
            isDraft = inputData.getBoolean(GUIDE_IS_DRAFT_KEY, true),
            isFree = inputData.getBoolean(GUIDE_TITLE_KEY, false),
            latestModified = inputData.getLong(GUIDE_TITLE_KEY, -1L),
            images = inputData.getStringArray(GUIDE_IMAGES_KEY)?.toList()
        ),
        inputData.getBoolean(
            DELETE_REQUEST_KEY,
            defaultValue = false
        )
    )

    private fun createNotification(appContext: Context): Notification =
        NotificationCompat.Builder(appContext, UPLOAD_NOTIFICATION_CHANNEL_ID)
            .setContentTitle("My upload notification")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

    companion object {
        private const val UPLOAD_NOTIFICATION_CHANNEL_ID = "Upload Notification Channel"
        private const val UPLOAD_NOTIFICATION_ID = 1

        const val DELETE_REQUEST_KEY = "delete_request"

        const val GUIDE_ID_KEY = "guide_id"
        const val GUIDE_TITLE_KEY = "guide_title"
        const val GUIDE_CONTENT_KEY = "guide_content"
        const val GUIDE_IS_DRAFT_KEY = "guide_isDraft"
        const val GUIDE_IS_FREE_KEY = "guide_isFree"
        const val GUIDE_LATEST_MODIFIED_KEY = "guide_latestModified"
        const val GUIDE_IMAGES_KEY = "guide_images"
    }
}
