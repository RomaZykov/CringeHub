package com.example.data.impl.admin.guide

import com.example.database.core.admin.GuideLocalDataSource
import com.example.domain.model.Guide
import com.example.domain.repositories.admin.guide.GuideRepository
import com.example.network.core.admin.GuideNetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GuideRepositoryImpl @Inject constructor(
    private val networkDataSource: GuideNetworkDataSource,
    private val cacheDataSource: GuideLocalDataSource
) : GuideRepository.Admin {

    override suspend fun synchronize() {
//        val userData = networkDataSource.fetchUserData()
//        localDataSource.saveUserData(userData)
    }

    override suspend fun updateGuide(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveGuideAsDraft() {
        // При нажатии на кнопку назад -> показать диалоговое окно, если title или content не пустой
        // сохранить изменения в качестве черновика: сохранить / нет
        // если есть интернет соединение в network
        // если нет в cache
//        val newGuide = GuideData()
//        cacheDataSource.saveGuideAsDraft(newGuide.mappedValue())
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