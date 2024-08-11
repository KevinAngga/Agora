package com.angga.agora.domain.account

interface AccountRepository {
    suspend fun logout()
}