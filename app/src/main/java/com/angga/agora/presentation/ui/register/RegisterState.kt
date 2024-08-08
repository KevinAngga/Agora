package com.angga.agora.presentation.ui.register

import androidx.compose.foundation.text.input.TextFieldState
import com.angga.agora.domain.validation.PasswordValidationState

data class RegisterState(
    val email : TextFieldState = TextFieldState(),
    val isEmailValid : Boolean = false,
    val password : TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isRegistering : Boolean = false, //loading
    /** val canRegister : Boolean = passwordValidationState.isValidPassword && !isRegistering
     * button enabled, cant do validatation on this because each type will trigger recomposition
     * and we do copy the state, so it will make state not properly update
     * **/
    val canRegister : Boolean = false
)