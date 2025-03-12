package com.example.domain.model

data class Guide(
    val id: Int,
    val title: String,
    val content: String,
    val isFree: Boolean,
    val images: List<String>
)
