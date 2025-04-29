package com.example.test.repository

import com.example.domain.model.GuideDomain
import com.example.domain.repositories.admin.guide.GuideRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAdminGuideRepository : GuideRepository.Admin {
    val guides = mutableListOf<GuideDomain>()
    override fun fetchDraftGuides(): Flow<List<GuideDomain>> = flow {
        emit(guides.filter { it.isDraft })
    }

    override suspend fun updateGuide(guideId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveGuideAsDraft(title: String, content: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGuide(guideId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun publishGuide() {
        TODO("Not yet implemented")
    }

    override suspend fun syncWithNetwork(): Boolean {
        TODO("Not yet implemented")
    }

    override fun fetchPublishedGuides(): Flow<List<GuideDomain>> = flow {
        emit(guides.filter { !it.isDraft })
    }
}