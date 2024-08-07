package com.angga.agora.presentation.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.agora.domain.UserDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    init {
        snapshotFlow { state.email.text }.onEach { email ->
            val isValidEmail =  userDataValidator.isValidEmail(email.toString())
            state = state.copy(
                isEmailValid =  isValidEmail,
                canRegister = isValidEmail && state.passwordValidationState.isValidPassword
                        && !state.isRegistering

            )
        }.launchIn(viewModelScope)

        snapshotFlow { state.password.text }.onEach { password ->
            val passwordValidationState =  userDataValidator.isValidatePassword(password.toString())
            state = state.copy(
                passwordValidationState = passwordValidationState,
                canRegister = state.isEmailValid && state.passwordValidationState.isValidPassword
                        && !state.isRegistering

            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when(action) {
            RegisterAction.OnRegisterClick -> {}
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            else -> Unit
        }
    }
}