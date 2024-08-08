package com.angga.agora.data

import android.content.SharedPreferences
import com.angga.agora.data.Constant.KEY_AUTH_INFO
import com.angga.agora.data.dto.AuthInfoDto
import com.angga.agora.data.mapper.toAuthInfo
import com.angga.agora.data.mapper.toAuthInfoDto
import com.angga.agora.domain.model.AuthInfo
import com.angga.agora.domain.session.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class EncryptedSessionStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionStorage {
    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                Json.decodeFromString<AuthInfoDto>(it).toAuthInfo()
            }
        }
    }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                sharedPreferences.edit().remove(KEY_AUTH_INFO).apply()
                return@withContext
            }

            val json = Json.encodeToString(info.toAuthInfoDto())
            sharedPreferences.edit().putString(KEY_AUTH_INFO, json).apply()
        }
    }

}