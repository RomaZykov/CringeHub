package com.example.domain.repositories.admin.guide

import com.example.domain.model.Guide
import kotlinx.coroutines.flow.Flow

interface GuideRepository {

    suspend fun syncWithNetwork(): Boolean

    fun fetchNonDraftGuides(): Flow<List<Guide>>

    interface Admin : GuideRepository {

        fun fetchDraftGuides(): Flow<List<Guide>>

        suspend fun updateGuide(guideId: Long)

        suspend fun saveGuideAsDraft(title: String, content: String)

        suspend fun deleteGuide(guideId: Long)

        suspend fun publishGuide()
    }
}
