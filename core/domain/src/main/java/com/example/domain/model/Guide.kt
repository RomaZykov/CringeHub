package com.example.domain.model

data class Guide(
    val id: Long,
    val title: String,
    val content: String,
    val isFree: Boolean,
    val isDraft: Boolean,
    val images: List<String>
)
