package com.example.domain.model

import android.net.Uri

data class GuideDomain(
    val id: String,
    val title: String,
    val content: String,
    val isFree: Boolean,
    val isDraft: Boolean,
    val media: List<Uri>
)
