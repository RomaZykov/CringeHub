package com.example.workmanager

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//@HiltAndroidTest
//class RefreshLatestGuidesWorkerTest {
//
//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)
//
//    private val context get() = InstrumentationRegistry.getInstrumentation().context
//
//    @Before
//    fun setup() {
//        val config = Configuration.Builder()
//            .setMinimumLoggingLevel(Log.DEBUG)
//            .setExecutor(SynchronousExecutor())
//            .build()
//
//        // Initialize WorkManager for instrumentation tests.
//        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
//    }
//
//    @Test
//    fun testSyncWorker() {
//        // For coroutine worker
//        val worker = TestListenableWorkerBuilder<RefreshLatestGuidesWorker>(context).build()
//        runBlocking {
//            val result = worker.doWork()
//            kotlin.test.assertEquals(result, `is`(Result.success()))
//        }
//    }
//
//    @Test
//    fun testSyncWork() {
//        // Create request
//        val request = RefreshLatestGuidesWorker.startUpSyncWork()
//
//        val workManager = WorkManager.getInstance(context)
//        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)!!
//
//        // Enqueue and wait for result.
//        workManager.enqueue(request).result.get()
//
//        // Get WorkInfo and outputData
//        val preRunWorkInfo = workManager.getWorkInfoById(request.id).get()
//
//        // Assert
//        kotlin.test.assertEquals(WorkInfo.State.ENQUEUED, preRunWorkInfo?.state)
//
//        // Tells the testing framework that the constraints have been met
//        testDriver.setAllConstraintsMet(request.id)
//
//        val postRequirementWorkInfo = workManager.getWorkInfoById(request.id).get()
//        kotlin.test.assertEquals(WorkInfo.State.RUNNING, postRequirementWorkInfo?.state)
//    }
//}
