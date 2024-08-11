package com.angga.agora.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.angga.agora.data.Constant.AGORA_PREF
import com.angga.agora.data.EncryptedSessionStorage
import com.angga.agora.domain.session.SessionStorage
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }


    @Provides
    @Singleton
    fun provideEncryptedPreference(
        @ApplicationContext context: Context
    ) : SharedPreferences {
        return EncryptedSharedPreferences(
            context,
            AGORA_PREF,
            masterKey = MasterKey(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideSessionStorage(
        sharedPreferences: SharedPreferences
    ) : SessionStorage {
        return EncryptedSessionStorage(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRctEngine(@ApplicationContext context: Context) : RtcEngine {
        return RtcEngine.create(
            RtcEngineConfig().apply {
                mContext = context
            }
        )
    }

}