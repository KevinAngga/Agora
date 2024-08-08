package com.angga.agora.domain.utils

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class FirebaseError : DataError {
        FirebaseAuthInvalidUserException,
        FirebaseAuthInvalidCredentialsException,
        FirebaseAuthUserCollisionException,
        FirebaseAuthEmailException,
        FirebaseAuthActionCodeException,
        FirebaseAuthRecentLoginRequiredException,
        FirebaseAuthWebException,
        FirebaseAuthMultiFactorException,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL
    }
}