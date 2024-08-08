package com.angga.agora.domain.utils

import com.angga.agora.R
import com.angga.agora.presentation.ui.utils.UiText

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> {
            UiText.StringResource(R.string.error_disk_full)
        }

        DataError.Network.REQUEST_TIMEOUT -> {
            UiText.StringResource(R.string.error_request_timeout)
        }

        DataError.Network.TOO_MANY_REQUESTS -> {
            UiText.StringResource(R.string.error_too_many_request)
        }

        DataError.Network.NO_INTERNET -> {
            UiText.StringResource(R.string.error_no_internet)
        }

        DataError.Network.PAYLOAD_TOO_LARGE -> {
            UiText.StringResource(R.string.error_payload_too_large)
        }

        DataError.Network.SERVER_ERROR -> {
            UiText.StringResource(R.string.error_server)
        }

        DataError.Network.SERIALIZATION -> {
            UiText.StringResource(R.string.error_serialization)
        }

        DataError.FirebaseError.FirebaseAuthInvalidUserException -> {
            UiText.StringResource(R.string.firebase_error_invalid_user)
        }

        DataError.FirebaseError.FirebaseAuthInvalidCredentialsException -> {
            UiText.StringResource(R.string.firebase_invalid_credentials)
        }

        DataError.FirebaseError.FirebaseAuthUserCollisionException -> {
            UiText.StringResource(R.string.firebase_error_email_exist)
        }

        DataError.FirebaseError.FirebaseAuthEmailException -> {
            UiText.StringResource(R.string.firebase_error_auth_email)
        }

        DataError.FirebaseError.FirebaseAuthActionCodeException -> {
            UiText.StringResource(R.string.firebase_error_auth_code_exception)
        }

        DataError.FirebaseError.FirebaseAuthRecentLoginRequiredException -> {
            UiText.StringResource(R.string.firebase_error_recent_login)
        }

        DataError.FirebaseError.FirebaseAuthWebException -> {
            UiText.StringResource(R.string.firebase_error_auth_web)
        }

        DataError.FirebaseError.FirebaseAuthMultiFactorException -> {
            UiText.StringResource(R.string.firebase_error_multi_factor)
        }

        else -> {
            UiText.StringResource(R.string.error_unknown)
        }
    }
}