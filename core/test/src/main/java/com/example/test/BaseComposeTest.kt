package com.example.test

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule

abstract class BaseComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    val restorationTester: StateRestorationTester = StateRestorationTester(composeTestRule)

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    protected fun string(@StringRes stringRes: Int): String = context.getString(stringRes)
}
