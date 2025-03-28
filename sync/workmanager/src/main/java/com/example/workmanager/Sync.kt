package com.example.workmanager

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.workmanager.workers.SyncLatestGuidesWorker
import java.util.concurrent.TimeUnit

object Sync {
    fun initialize(context: Context) {
        val syncWorkRequest = OneTimeWorkRequestBuilder<SyncLatestGuidesWorker>()
            .setConstraints(getWorkConstraints())
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                15, TimeUnit.SECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            SyncLatestGuidesWorker.SYNC_GUIDES_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            syncWorkRequest
        )
    }

    private fun getWorkConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
}