package com.example.network.firebase

import com.google.firebase.FirebaseException

// TODO: hardcode and rework descriptions
open class FirebaseCustomException(val code: Int, message: String) : FirebaseException(message)

class NoUserAuthInDatabase :
    FirebaseCustomException(NO_USER_AUTH_IN_BACKEND, "User is not authorize")

class NoUserInfoFoundInDatabase :
FirebaseCustomException(NO_USER_INFO_FOUND_IN_DATABASE, "User is not found in database. Our fault, sorry!")

const val NO_USER_AUTH_IN_BACKEND = 1001
const val NO_USER_INFO_FOUND_IN_DATABASE = 1002
