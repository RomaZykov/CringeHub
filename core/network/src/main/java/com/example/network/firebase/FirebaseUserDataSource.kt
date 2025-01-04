package com.example.network.firebase

import com.example.network.core.UserNetworkDataSource
import com.example.network.model.UserNetwork
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class FirebaseUserDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserNetworkDataSource {

    override suspend fun signInUserWithFirebaseCredential(firebaseCredential: AuthCredential) {
        auth.signInWithCredential(firebaseCredential).addOnSuccessListener { task ->
            val isNewUser = task.additionalUserInfo?.isNewUser ?: throw NoUserAuthInDatabase()
            if (isNewUser) {
                auth.currentUser?.apply {
                    val userNetwork = UserNetwork(this.uid, this.displayName, true)
                    db.collection(USERS).document(uid).set(userNetwork)
                }
            }
        }
    }

    override suspend fun getUser(): UserNetwork {
        val usersRef = db.collection(USERS)
        val currentUser = auth.currentUser ?: throw NoUserAuthInDatabase()
        val document = usersRef.document(currentUser.uid)
        val snapshot = document.get().await()
        return snapshot.toObject<UserNetwork>() ?: throw NoUserInfoFoundInDatabase()
    }

    override suspend fun signOutUser() {
        auth.signOut()
    }

    companion object {
        private const val USERS = "users"
    }
}