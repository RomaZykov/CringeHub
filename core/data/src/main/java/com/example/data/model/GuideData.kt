package com.example.data.model

data class GuideData(
    val id: String,
    val title: String,
    val content: String,
    val latestModified: Long,
    val isDraft: Boolean,
    val isFree: Boolean,
    val images: List<String>
)