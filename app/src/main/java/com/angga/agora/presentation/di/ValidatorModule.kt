package com.angga.agora.presentation.di

import com.angga.agora.data.EmailPatternValidator
import com.angga.agora.domain.PatternValidator
import com.angga.agora.domain.UserDataValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidatorModule {

    @Provides
    @Singleton
    fun providePatternValidator() : PatternValidator {
        return EmailPatternValidator()
    }

    @Provides
    @Singleton
    fun provideUserDataValidator(
        emailPatternValidator: EmailPatternValidator
    ): UserDataValidator {
        return UserDataValidator(patternValidator = emailPatternValidator)
    }

}