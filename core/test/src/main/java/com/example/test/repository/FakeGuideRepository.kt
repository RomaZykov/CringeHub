package com.example.test.repository

import com.example.domain.model.Guide
import com.example.domain.repositories.admin.guide.GuideRepository
import kotlinx.coroutines.flow.Flow

class FakeAdminGuideRepository : GuideRepository.Admin {
    override suspend fun synchronize() {
        TODO("Not yet implemented")
    }

    override suspend fun updateGuide(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveGuideAsDraft() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGuide() {
        TODO("Not yet implemented")
    }

    override suspend fun publishGuide() {
        TODO("Not yet implemented")
    }

    override fun fetchGuides(): Flow<List<Guide>> {
        TODO("Not yet implemented")
    }
}