package com.angga.agora.data

import com.angga.agora.domain.utils.DataError
import com.angga.agora.domain.utils.Result
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthMultiFactorException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWebException


fun handleFirebaseAuthException(firebaseAuthException: FirebaseAuthException) : DataError.FirebaseError {
    return when (firebaseAuthException) {
        is FirebaseAuthInvalidUserException -> {
            DataError.FirebaseError.FirebaseAuthInvalidUserException
        }
        is FirebaseAuthInvalidCredentialsException -> {
            DataError.FirebaseError.FirebaseAuthInvalidCredentialsException
        }
        is FirebaseAuthUserCollisionException -> {
            DataError.FirebaseError.FirebaseAuthUserCollisionException
        }
        is FirebaseAuthEmailException -> {
            DataError.FirebaseError.FirebaseAuthEmailException
        }
        is FirebaseAuthActionCodeException -> {
            DataError.FirebaseError.FirebaseAuthActionCodeException
        }
        is FirebaseAuthRecentLoginRequiredException -> {
            DataError.FirebaseError.FirebaseAuthRecentLoginRequiredException
        }
        is FirebaseAuthWebException -> {
            DataError.FirebaseError.FirebaseAuthWebException
        }
        is FirebaseAuthMultiFactorException -> {
            DataError.FirebaseError.FirebaseAuthMultiFactorException
        }
        else -> {
            DataError.FirebaseError.UNKNOWN
        }
    }
}