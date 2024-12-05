package com.example.network.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.example.network.core.UserNetworkDataSource
import com.example.network.model.UserNetwork
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class FirebaseDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserNetworkDataSource {

    override suspend fun signInUserWithFirebaseCredential(firebaseCredential: AuthCredential) {
        auth.signInWithCredential(firebaseCredential).addOnCompleteListener {
            val isNewUser =
                it.result.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                auth.currentUser?.apply {
                    val userNetwork = UserNetwork(this.uid, this.displayName)
                    db.collection(USERS).document(uid).set(userNetwork)
                        .addOnSuccessListener {
                            Log.d(
                                TAG,
                                "DocumentSnapshot successfully written!"
                            )
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                }
            }
        }
    }

    override suspend fun getUser(): UserNetwork {
        val userRef = db.collection(USERS).document(auth.uid!!)
        val snapshot = userRef.get().await()
        val user = snapshot.toObject<UserNetwork>()
        return user!!
    }

    override suspend fun signOutUser() {
        auth.signOut()
    }

    companion object {
        private const val USERS = "users"
    }
}
