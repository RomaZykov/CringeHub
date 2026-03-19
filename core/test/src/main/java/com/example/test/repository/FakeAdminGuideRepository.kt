package com.example.test.repository

import com.example.domain.model.GuideDomain
import com.example.domain.repositories.admin.guide.GuideRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeAdminGuideRepository @Inject constructor() : GuideRepository.Admin {

    val guides = mutableListOf<GuideDomain>()

    override fun fetchDraftGuides(): Flow<List<GuideDomain>> = flow {
        emit(guides.filter { it.isDraft })
    }

    override suspend fun upsertGuide(id: String, title: String, content: String) {
        var indexToChange = -1
        guides.forEachIndexed { index, guideDomain ->
            if (guideDomain.id == id) {
                indexToChange = index
                guides[index] = guideDomain.copy(title, content)
            }
        }
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