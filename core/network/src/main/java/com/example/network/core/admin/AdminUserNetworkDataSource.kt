package com.example.network.core.admin

import com.example.network.exceptions.FirebaseCustomException
import com.example.network.model.AdminUserNetwork
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AdminUserNetworkDataSource {

    suspend fun signInUserWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun signOutUser()

    suspend fun getUser(currentAdminUser: FirebaseUser?): AdminUserNetwork?

    class Base @Inject constructor(
        private val auth: FirebaseAuth,
        private val db: FirebaseFirestore
    ) : AdminUserNetworkDataSource {
        override suspend fun signInUserWithEmailAndPassword(
            email: String,
            password: String
        ): AuthResult {
            return auth.signInWithEmailAndPassword(email, password).await()
        }

        override suspend fun signOutUser() {
            auth.signOut()
        }

        override suspend fun getUser(currentAdminUser: FirebaseUser?): AdminUserNetwork {
            val adminUsersRef = db.collection(ADMIN_USERS)
            val currentUser =
                currentAdminUser ?: throw FirebaseCustomException.NoUserAuthInDatabase()
            val document = adminUsersRef.document(currentUser.uid)
            val snapshot = document.get().await()
            return snapshot.toObject<AdminUserNetwork>()
                ?: throw FirebaseCustomException.NoUserInfoFoundInDatabase()
        }
    }

    companion object {
        private const val ADMIN_USERS = "admins"
    }
}