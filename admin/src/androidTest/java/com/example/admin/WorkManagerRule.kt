package com.example.admin

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import org.junit.rules.TestRule
import org.junit.runner.Description

class WorkManagerRule : TestRule {
    override fun apply(
        base: org.junit.runners.model.Statement?,
        description: Description?
    ): org.junit.runners.model.Statement {

        return object : org.junit.runners.model.Statement() {
            override fun evaluate() {
                val context = InstrumentationRegistry.getInstrumentation().targetContext
                val config = Configuration.Builder()
                    .setMinimumLoggingLevel(Log.DEBUG)
                    .setExecutor(SynchronousExecutor())
                    .build()
                WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
                try {
                    base?.evaluate()
                } finally {
                    Log.d("WorkManagerRule", "Do some teardown")
                }
            }
        }
    }
}