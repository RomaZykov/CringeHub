package com.example.test.repository

import com.example.domain.model.GuideDomain
import com.example.domain.repositories.admin.guide.GuideRepository
import kotlinx.coroutines.flow.Flow

class FakeGuideRepository : GuideRepository {
    override suspend fun syncWithNetwork(): Boolean {
        TODO("Not yet implemented")
    }

    override fun fetchPublishedGuides(): Flow<List<GuideDomain>> {
        TODO("Not yet implemented")
    }
}
