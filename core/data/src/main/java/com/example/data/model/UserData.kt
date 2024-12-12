package com.example.data.model

import com.example.domain.Mapper
import com.example.domain.model.User

data class UserData(val id: String, val userName: String, val isNewUser: Boolean) : Mapper<User> {

    override fun mappedValue(): User {
        return User(this.id, this.userName, this.isNewUser)
    }
}