package com.example.test.repository

import com.example.domain.model.GuideDomain
import com.example.domain.repositories.admin.guide.GuideRepository
import kotlinx.coroutines.flow.Flow

class FakeAdminGuideRepository : GuideRepository.Admin {
    override fun fetchDraftGuides(): Flow<List<GuideDomain>> {
        TODO("Not yet implemented")
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

    override fun fetchNonDraftGuides(): Flow<List<GuideDomain>> {
        TODO("Not yet implemented")
    }
}