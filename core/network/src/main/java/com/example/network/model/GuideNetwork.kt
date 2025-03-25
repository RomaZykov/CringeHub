package com.example.network.model

data class GuideNetwork(
    val id: Long? = null,
    val title: String? = null,
    val content: String? = null,
    val isDraft: Boolean? = null,
    val isFree: Boolean? = null,
    val latestModified: Long? = null,
    // TODO: Replace with correct type for images
    val images: List<String>? = null
)