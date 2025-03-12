package com.example.domain.repositories.admin.guide

import com.example.domain.model.Guide
import kotlinx.coroutines.flow.Flow

interface GuideRepository {

    suspend fun synchronize()

    fun fetchGuides(): Flow<List<Guide>>

    interface Admin : GuideRepository {

        suspend fun updateGuide(id: String)

        suspend fun saveGuideAsDraft()

        suspend fun deleteGuide()

        suspend fun publishGuide()
    }
}
