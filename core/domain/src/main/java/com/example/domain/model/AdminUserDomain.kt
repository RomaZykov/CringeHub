package com.example.domain.model

data class AdminUserDomain(
    val email: String,
    val adminRole: AdminRole
)

enum class AdminRole {
    CONTENT_WRITER, ALL_ACCESS
}
