package com.example.data.model

data class GuideData(
    val id: String,
    val title: String = "",
    val content: Map<Int, String> = mapOf(0 to ""),
    val latestModified: Long = -1L,
    val isDraft: Boolean = true,
    val isFree: Boolean = false,
    val images: List<String> = emptyList()
)