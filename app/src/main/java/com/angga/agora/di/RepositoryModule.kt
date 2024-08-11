package com.angga.agora.di

import android.content.Context
import com.angga.agora.data.LiveStreamRepositoryImpl
import com.angga.agora.data.account.AccountRepositoryImpl
import com.angga.agora.data.auth.AuthRepositoryImpl
import com.angga.agora.domain.account.AccountRepository
import com.angga.agora.domain.auth.AuthRepository
import com.angga.agora.domain.live.LiveStreamRepository
import com.angga.agora.domain.session.SessionStorage
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        sessionStorage: SessionStorage
    ) : AuthRepository {
        return AuthRepositoryImpl(
            firebaseAuth = auth,
            sessionStorage = sessionStorage
        )
    }


    @Provides
    @Singleton
    fun provideLiveStreamRepository(
        @ApplicationContext context: Context
    ) : LiveStreamRepository {
        return LiveStreamRepositoryImpl(
            appContext = context
        )
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        auth: FirebaseAuth,
        sessionStorage: SessionStorage
    ) : AccountRepository {
        return AccountRepositoryImpl(
            firebaseAuth = auth,
            sessionStorage = sessionStorage
        )
    }
}