package com.angga.agora.domain.session

import com.angga.agora.domain.model.AuthInfo

interface SessionStorage {
    suspend fun get() : AuthInfo?
    suspend fun set(info: AuthInfo?)
}