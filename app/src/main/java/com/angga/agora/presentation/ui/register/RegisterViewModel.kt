package com.angga.agora.presentation.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.agora.domain.auth.AuthRepository
import com.angga.agora.domain.utils.Result
import com.angga.agora.domain.utils.asUiText
import com.angga.agora.domain.validation.UserDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val eventChanel = Channel<RegisterEvent>()
    val events = eventChanel.receiveAsFlow()

    init {
        /**
         * combine as singleFlow because if we make it on 2 flows the data will
         * inconsistent cause updated state from 2 diff flow
         **/
        val emailAndPasswordFlow = combine(
            snapshotFlow { state.email.text },
            snapshotFlow { state.password.text }
        ) { email, password ->
            email  to password
        }

        emailAndPasswordFlow.onEach { (email, password) ->
            val isValidEmail =  userDataValidator.isValidEmail(email.toString())
            val passwordValidationState =  userDataValidator.isValidatePassword(password.toString())

            state = state.copy(
                isEmailValid = isValidEmail,
                passwordValidationState = passwordValidationState,
                canRegister = isValidEmail && passwordValidationState.isValidPassword && !state.isRegistering
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when(action) {
            RegisterAction.OnRegisterClick -> { register() }
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)

            val result = authRepository.register(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString().trim()
            )

            state = state.copy(isRegistering = false)

           when(result) {
               is Result.Failed -> {
                   eventChanel.send(RegisterEvent.Error(result.error.asUiText()))
               }

               is Result.Success -> {
                   eventChanel.send(RegisterEvent.RegistrationSuccess)
               }
           }
        }
    }
}