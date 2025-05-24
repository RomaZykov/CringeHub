package com.example.adminhome.model

data class GuideUi(
    val id: String,
    val title: String,
    val content: String,
    val isFree: Boolean,
    val isDraft: Boolean
)