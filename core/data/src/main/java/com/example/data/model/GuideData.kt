package com.example.data.model

data class GuideData(
    val id: String,
    val title: String = "",
    val content: String = "",
    val latestModified: Long = -1L,
    val isDraft: Boolean = true,
    val isFree: Boolean = false,
    val images: List<String> = emptyList()
)