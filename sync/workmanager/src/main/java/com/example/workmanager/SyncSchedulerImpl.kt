package com.example.workmanager

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.data.SyncScheduler
import com.example.network.model.GuideNetwork
import com.example.workmanager.workers.UploadGuideChangesWorker
import com.example.workmanager.workers.UploadGuideChangesWorker.Companion.GUIDE_CONTENT_KEY
import com.example.workmanager.workers.UploadGuideChangesWorker.Companion.GUIDE_ID_KEY
import com.example.workmanager.workers.UploadGuideChangesWorker.Companion.GUIDE_IMAGES_KEY
import com.example.workmanager.workers.UploadGuideChangesWorker.Companion.GUIDE_IS_DRAFT_KEY
import com.example.workmanager.workers.UploadGuideChangesWorker.Companion.GUIDE_IS_FREE_KEY
import com.example.workmanager.workers.UploadGuideChangesWorker.Companion.GUIDE_LATEST_MODIFIED_KEY
import com.example.workmanager.workers.UploadGuideChangesWorker.Companion.GUIDE_TITLE_KEY
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class SyncSchedulerImpl @Inject constructor(
    private val workManager: WorkManager
) : SyncScheduler {

    override fun scheduleUploadGuideWork(guide: GuideNetwork) {
        val syncWorkRequest = OneTimeWorkRequestBuilder<UploadGuideChangesWorker>()
            .setConstraints(getWorkConstraints())
            .setInputData(generateInputData(guide))
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                15, TimeUnit.SECONDS
            )
            .build()

        workManager.enqueueUniqueWork(
            setUniqueWorkerName(guide.id),
            ExistingWorkPolicy.REPLACE,
            syncWorkRequest
        )
    }

    private fun generateInputData(guide: GuideNetwork) : Data {
        return workDataOf(
            GUIDE_ID_KEY to guide.id,
            GUIDE_TITLE_KEY to guide.title,
            GUIDE_CONTENT_KEY to guide.content,
            GUIDE_IS_DRAFT_KEY to guide.isDraft,
            GUIDE_IS_FREE_KEY to guide.isFree,
            GUIDE_LATEST_MODIFIED_KEY to guide.latestModified,
            GUIDE_IMAGES_KEY to (guide.images?.toTypedArray() ?: emptyList<String>().toTypedArray())
        )
    }

    private fun getWorkConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private fun setUniqueWorkerName(guideId: String?): String =
        "UploadGuideWorker with guideId = $guideId"
}