package com.example.data.model

import com.example.domain.Mapper
import com.example.domain.model.AdminRole
import com.example.domain.model.AdminUser

data class AdminUserData(val email: String) : Mapper<AdminUser> {
    override fun mappedValue(): AdminUser {
        return AdminUser(this.email, AdminRole.CONTENT_WRITER)
    }
}
