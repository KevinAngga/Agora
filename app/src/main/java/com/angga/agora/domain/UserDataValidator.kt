package com.angga.agora.domain

import com.angga.agora.data.EmailPatternValidator
import javax.inject.Inject

class UserDataValidator @Inject constructor(val patternValidator: EmailPatternValidator) {
    fun isValidEmail(email : String) : Boolean {
        return patternValidator.matches(email.trim())
    }

    fun isValidatePassword(password : String) : PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasNumber = password.any { it.isDigit() }
        val hasLowerCaseCharacter = password.any { it.isLowerCase() }
        val hasUpperCaseCharacter = password.any { it.isUpperCase() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumber = hasNumber,
            hasLowerCaseCharacter = hasLowerCaseCharacter,
            hasUpperCaseCharacter = hasUpperCaseCharacter
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}