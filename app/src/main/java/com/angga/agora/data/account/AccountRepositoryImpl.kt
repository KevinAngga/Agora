package com.angga.agora.data.account

import com.angga.agora.domain.account.AccountRepository
import com.angga.agora.domain.session.SessionStorage
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val sessionStorage: SessionStorage
) : AccountRepository {
    override suspend fun logout() {
        firebaseAuth.signOut()
        if (firebaseAuth.currentUser?.email == null) {
            println("===== jalan signOut")
            sessionStorage.set(null)
        }
    }
}