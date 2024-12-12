package com.example.database.core

import com.example.database.enteties.UserLocal

interface UserLocalDataSource {

    fun getUser(uid: String): UserLocal
}