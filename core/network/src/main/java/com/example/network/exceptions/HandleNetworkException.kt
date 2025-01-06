package com.example.network.exceptions

import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.example.common.core.HandleError
import com.example.common.core.ManageResources
import com.example.network.R
import com.example.network.exceptions.FirebaseCustomException.Companion.NO_USER_AUTH_IN_BACKEND
import com.example.network.exceptions.FirebaseCustomException.Companion.NO_USER_INFO_FOUND_IN_DATABASE
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

// TODO: hardcode
class HandleNetworkException @Inject constructor(private val manageResources: ManageResources) :
    HandleError {
    override fun handle(error: Exception): Throwable {
        return when (error) {
            is FirebaseCustomException -> {
                when (error.code) {
                    NO_USER_AUTH_IN_BACKEND -> FirebaseCustomException.NoUserAuthInDatabase(
                        manageResources.string(R.string.user_is_not_authorize)
                    )

                    NO_USER_INFO_FOUND_IN_DATABASE -> FirebaseCustomException.NoUserInfoFoundInDatabase(
                        manageResources.string(R.string.user_is_not_found_in_database)
                    )

                    else -> Exception(manageResources.string(R.string.unexpected_network_exception))
                }
            }

            // TODO: replace by runCatchingCancellation
            is GetCredentialCancellationException -> {
                error.message.let {
                    if (it!!.contains(manageResources.string(R.string.keyword_message_for_no_internet_connection)))
                        Exception(manageResources.string(R.string.no_internet_exception))
                    else
                        throw CancellationException()
                }
            }

            else -> {
                Exception(manageResources.string(R.string.unexpected_exception))
            }
        }
    }
}