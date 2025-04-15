package com.example.data.core

import com.example.data.model.AdminUserData
import com.example.domain.model.AdminRole
import com.example.domain.model.AdminUserDomain
import com.example.network.model.AdminUserNetwork
import javax.inject.Inject

interface AdminUserMapperFactory {
    fun <T> mapToDomain(input: T): AdminUserDomain

    class Base @Inject constructor() : AdminUserMapperFactory {

        override fun <T> mapToDomain(input: T): AdminUserDomain {
            return when (input) {
                is AdminUserData -> {
                    AdminUserDomain(
                        email = input.email,
                        adminRole = AdminRole.CONTENT_WRITER
                    )
                }

                else -> throw IllegalArgumentException("Unknown admin specific input type: $input")
            }
        }
    }
}