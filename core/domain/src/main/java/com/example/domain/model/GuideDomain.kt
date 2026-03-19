package com.example.domain.model

import android.net.Uri

data class GuideDomain(
    val id: String,
    val title: String,
    val content: Map<Int, String>,
    val isFree: Boolean,
    val isDraft: Boolean,
    val media: List<Uri>
)
