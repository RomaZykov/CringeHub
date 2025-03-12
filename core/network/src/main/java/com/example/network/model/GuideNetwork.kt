package com.example.network.model

data class GuideNetwork(
    val id: Int,
    val title: String,
    val content: String,
    val isFree: Boolean,
    val images: List<String>
)
