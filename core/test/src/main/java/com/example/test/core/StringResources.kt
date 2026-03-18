package com.example.test.core

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry

abstract class StringResources {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    protected fun retrieveString(@StringRes stringRes: Int, vararg args: Any): String =
        context.getString(stringRes, *args)
}