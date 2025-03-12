package com.example.data.impl.admin.auth

import com.example.data.model.AdminUserData
import com.example.domain.model.AdminUser
import com.example.domain.repositories.AuthRepository
import com.example.network.core.admin.AdminUserNetworkDataSource
import javax.inject.Inject

class AuthAdminEmailRepositoryImpl @Inject constructor(private val adminUserNetworkDataSource: AdminUserNetworkDataSource) :
    AuthRepository.AdminAuthRepository {

    override suspend fun signInWithEmail(email: String, password: String): Result<AdminUser> {
        return try {
            val currentAdminUser = adminUserNetworkDataSource.signInUserWithEmailAndPassword(
                email,
                password
            ).user
            val rawAdminUser = adminUserNetworkDataSource.getUser(currentAdminUser)
            val adminUser = AdminUserData(
                rawAdminUser?.email.orEmpty()
            )
            Result.success(adminUser.mappedValue())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        adminUserNetworkDataSource.signOutUser()
    }
}