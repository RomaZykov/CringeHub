package com.example.common.core

interface HandleError {
    fun handle(error: Exception) : Throwable
}