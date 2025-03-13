package com.example.network.core.admin

import com.example.network.model.GuideNetwork
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GuideNetworkDataSource {

    fun allGuides(): Flow<List<GuideNetwork>>

    fun getGuide(id: Int): Flow<GuideNetwork>

//    suspend fun contains(number: String): Boolean
//
//    suspend fun saveNumber(numberData: NumberData)
//
//    class Base(
//        private val dao: NumbersDao,
//        private val dataToCache: NumberData.Mapper<NumberCache>
//    ) : NumbersCacheDataSource {
//
//        private val mutex = Mutex()
//
//        override suspend fun allNumbers(): List<NumberData> = mutex.withLock {
//            val data = dao.allNumbers()
//            return data.map { NumberData(it.number, it.fact) }
//        }
//
//        override suspend fun contains(number: String): Boolean = mutex.withLock {
//            val data = dao.number(number)
//            return data != null
//        }
//
//        override suspend fun saveNumber(numberData: NumberData) = mutex.withLock {
//            dao.insert(numberData.map(dataToCache))
//        }
//
//        override suspend fun number(number: String): NumberData = mutex.withLock {
//            val numberCache = dao.number(number) ?: NumberCache("", "", 0)
//            return NumberData(numberCache.number, numberCache.fact)
//        }
//    }

    class Base @Inject constructor(
        private val db: FirebaseFirestore
    ) : GuideNetworkDataSource {
        override fun allGuides(): Flow<List<GuideNetwork>> {
            TODO("Not yet implemented")
        }

        override fun getGuide(id: Int): Flow<GuideNetwork> {
            TODO("Not yet implemented")
        }
    }
}