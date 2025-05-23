package com.example.cringehub.admin

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CringeHubAdminApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .apply {
                setWorkerFactory(workerFactory)
                if (BuildConfig.DEBUG) {
                    setMinimumLoggingLevel(android.util.Log.DEBUG)
                } else {
                    setMinimumLoggingLevel(android.util.Log.ERROR)
                }
            }
            .build()
}