package com.example.data.core

import com.example.data.model.UserData
import com.example.domain.model.UserDomain
import javax.inject.Inject

interface UserMapperFactory {

    fun <T> mapToDomain(input: T): UserDomain

    class Base @Inject constructor() : UserMapperFactory {
        override fun <T> mapToDomain(input: T): UserDomain {
            return when (input) {
                is UserData -> {
                    UserDomain(
                        id = input.id,
                        userName = input.userName,
                        isLoggedIn = input.isLoggedIn,
                    )
                }

                else -> throw IllegalArgumentException("Unknown user specific input type: $input")
            }
        }
    }
}