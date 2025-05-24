package com.example.domain.model

data class GuideDomain(
    val id: String,
    val title: String,
    val content: String,
    val isFree: Boolean,
    val isDraft: Boolean,
    val images: List<String>
)
