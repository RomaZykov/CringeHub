package com.example.database.core

import com.example.database.entities.UserEntity

interface UserLocalDataSource {

    fun getUser(uid: String): UserEntity
}