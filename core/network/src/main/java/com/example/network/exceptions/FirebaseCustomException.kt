package com.example.network.exceptions

import com.google.firebase.FirebaseException

sealed class FirebaseCustomException(val code: Int, message: String) : FirebaseException(message) {

    class NoUserAuthInDatabase(message: String = "") :
        FirebaseCustomException(NO_USER_AUTH_IN_BACKEND, message)

    class NoUserInfoFoundInDatabase(message: String = "") :
        FirebaseCustomException(NO_USER_INFO_FOUND_IN_DATABASE, message)

    companion object {
        const val NO_USER_AUTH_IN_BACKEND = 1001
        const val NO_USER_INFO_FOUND_IN_DATABASE = 1002
    }
}
