package com.example.test.repository

import com.example.domain.model.AdminUserDomain
import com.example.domain.repositories.AuthRepository
import javax.inject.Inject

class FakeAdminAuthRepository @Inject constructor() : AuthRepository.Admin {
    override suspend fun signInWithEmail(email: String, password: String): Result<AdminUserDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}