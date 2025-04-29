package com.example.domain.repositories.admin.guide

import com.example.domain.model.GuideDomain
import kotlinx.coroutines.flow.Flow

interface GuideRepository {

    suspend fun syncWithNetwork(): Boolean

    fun fetchPublishedGuides(): Flow<List<GuideDomain>>

    interface Admin : GuideRepository {

        fun fetchDraftGuides(): Flow<List<GuideDomain>>

        suspend fun updateGuide(guideId: String)

        suspend fun saveGuideAsDraft(title: String, content: String)

        suspend fun deleteGuide(guideId: String)

        suspend fun publishGuide()
    }
}
