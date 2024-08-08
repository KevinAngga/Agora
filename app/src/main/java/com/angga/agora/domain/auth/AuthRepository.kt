package com.angga.agora.domain.auth

import com.angga.agora.domain.utils.DataError
import com.google.firebase.auth.AuthResult
import com.angga.agora.domain.utils.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthResult, DataError.FirebaseError>
    suspend fun register(email: String, password: String): Result<AuthResult, DataError.FirebaseError>
}