package com.example.network

import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.common.core.HandleError
import com.example.network.firebase.FirebaseCustomException
import com.example.network.firebase.NO_USER_AUTH_IN_BACKEND
import com.example.network.firebase.NO_USER_INFO_FOUND_IN_DATABASE
import com.example.network.firebase.NoUserAuthInDatabase
import com.example.network.firebase.NoUserInfoFoundInDatabase
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

// TODO: hardcode
class HandleNetworkException @Inject constructor() : HandleError {
    override fun handle(error: Exception): Throwable {
        return when (error) {
            is FirebaseCustomException -> {
                when (error.code) {
                    NO_USER_AUTH_IN_BACKEND -> NoUserAuthInDatabase()
                    NO_USER_INFO_FOUND_IN_DATABASE -> NoUserInfoFoundInDatabase()
                    else -> Exception("Unexpected network exception")
                }
            }

            is GetCredentialCancellationException -> {
                throw CancellationException()
            }

            else -> {
                Exception("Unexpected error. Check your Internet connection.")
            }
        }
    }
}