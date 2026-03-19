package com.example.common.core

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ProvideString {

    fun string(@StringRes id: Int): String

    class Base @Inject constructor(@ApplicationContext private val context: Context) : ProvideString {
        override fun string(id: Int) = context.getString(id)
    }
}
