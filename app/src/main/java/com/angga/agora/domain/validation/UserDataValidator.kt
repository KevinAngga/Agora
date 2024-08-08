package com.angga.agora.domain.validation

import com.angga.agora.data.validation.EmailPatternValidator
import javax.inject.Inject

class UserDataValidator @Inject constructor(val patternValidator: EmailPatternValidator) {
    fun isValidEmail(email : String) : Boolean {
        return patternValidator.matches(email.trim())
    }

    fun isValidatePassword(password : String) : PasswordValidationState {
        val trimmedPassword = password.trim()
        val hasMinLength = trimmedPassword.length >= MIN_PASSWORD_LENGTH
        val hasNumber = trimmedPassword.any { it.isDigit() }
        val hasLowerCaseCharacter = trimmedPassword.any { it.isLowerCase() }
        val hasUpperCaseCharacter = trimmedPassword.any { it.isUpperCase() }

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