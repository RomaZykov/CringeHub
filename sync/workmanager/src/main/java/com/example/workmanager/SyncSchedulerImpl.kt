package com.example.workmanager

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.data.SyncScheduler
import com.example.network.model.GuideNetwork
import com.example.workmanager.workers.UploadGuideChangesWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class SyncSchedulerImpl @Inject constructor(
    private val workManager: WorkManager
) : SyncScheduler {

    override fun scheduleUploadGuideWork(guide: GuideNetwork) {
        val syncWorkRequest = OneTimeWorkRequestBuilder<UploadGuideChangesWorker>()
            .setConstraints(getWorkConstraints())
            .setInputData(workDataOf(Pair(UploadGuideChangesWorker.GUIDE_KEY, guide)))
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                15, TimeUnit.SECONDS
            )
            .build()

        workManager.enqueueUniqueWork(
            setUniqueWorkerName(guide.id!!),
            ExistingWorkPolicy.REPLACE,
            syncWorkRequest
        )
    }

    private fun getWorkConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private fun setUniqueWorkerName(guideId: Long): String =
        "upload changes for guide with id = $guideId"
}