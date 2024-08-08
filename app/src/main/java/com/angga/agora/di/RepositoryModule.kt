package com.angga.agora.di

import com.angga.agora.data.auth.AuthRepositoryImpl
import com.angga.agora.domain.auth.AuthRepository
import com.angga.agora.domain.session.SessionStorage
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}