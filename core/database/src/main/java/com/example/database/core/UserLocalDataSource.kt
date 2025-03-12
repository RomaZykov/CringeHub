package com.example.database.core

import com.example.database.entities.UserLocal

interface UserLocalDataSource {

    fun getUser(uid: String): UserLocal
}