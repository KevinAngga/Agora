package com.angga.agora.data.auth

import com.angga.agora.data.dto.AuthInfoDto
import com.angga.agora.data.handleFirebaseAuthException
import com.angga.agora.data.mapper.toAuthInfo
import com.angga.agora.domain.auth.AuthRepository
import com.angga.agora.domain.model.AuthInfo
import com.angga.agora.domain.session.SessionStorage
import com.angga.agora.domain.utils.DataError
import com.angga.agora.domain.utils.Result
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val sessionStorage: SessionStorage
): AuthRepository {
    override suspend fun login(email: String, password: String): Result<AuthResult, DataError.FirebaseError> {
        return try {
            val response = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            sessionStorage.set(
                AuthInfoDto(
                    userUID = response.user?.uid ?: "dummy"
                ).toAuthInfo()
            )
            Result.Success(data = response)
        } catch (e : FirebaseAuthException) {
            Result.Failed(handleFirebaseAuthException(firebaseAuthException = e))
        }
    }

    override suspend fun register(email: String, password: String): Result<AuthResult, DataError.FirebaseError> {
        return try {
            val response = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.Success(data = response)
        } catch (e : FirebaseAuthException) {
            Result.Failed(handleFirebaseAuthException(firebaseAuthException = e))
        }
    }

}