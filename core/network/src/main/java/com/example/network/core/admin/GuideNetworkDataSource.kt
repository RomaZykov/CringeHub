package com.example.network.core.admin

import android.content.ContentValues.TAG
import android.util.Log
import com.example.network.model.GuideNetwork
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface GuideNetworkDataSource {

    fun allGuides(): Flow<List<GuideNetwork?>>

    fun allDraftGuides(): Flow<List<GuideNetwork?>>

    fun getGuide(id: String): Flow<GuideNetwork?>

    suspend fun upsertGuide(guideNetwork: GuideNetwork): Result<Boolean>

    suspend fun deleteGuide(guideId: String): Result<Boolean>

    class Base @Inject constructor(
        private val db: FirebaseFirestore
    ) : GuideNetworkDataSource {

        private val cachedNetworkGuides = mutableListOf<GuideNetwork>()

        override fun allGuides(): Flow<List<GuideNetwork>> = flow {
            val rawGuides = db.collection(GUIDES).get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting guides document: ", exception)
                }
            val networkGuides = rawGuides.await().toObjects(GuideNetwork::class.java)
            if (cachedNetworkGuides.isEmpty()) {
                cachedNetworkGuides.addAll(networkGuides)
            }
            emit(networkGuides)
        }

        override fun allDraftGuides(): Flow<List<GuideNetwork>> = flow {
            val draftGuides = cachedNetworkGuides.filter {
                it.isDraft == true
            }
            emit(draftGuides)
        }

        override fun getGuide(id: String): Flow<GuideNetwork?> = flow {
            val guide = cachedNetworkGuides.find {
                it.id == id
            }
            emit(guide)
        }

        override suspend fun upsertGuide(
            guideNetwork: GuideNetwork
        ): Result<Boolean> {
            return try {
                db.collection(GUIDES).document(guideNetwork.id.toString())
                    .set(guideNetwork)
                Result.success(true)
            } catch (e: FirebaseFirestoreException) {
                Result.failure(e)
            }
        }

        override suspend fun deleteGuide(guideId: String): Result<Boolean> {
            return try {
                db.collection(GUIDES).document(guideId).delete()
                Result.success(true)
            } catch (e: FirebaseFirestoreException) {
                Result.failure(e)
            }
        }

        companion object {
            const val GUIDES = "guides"
        }
    }
}