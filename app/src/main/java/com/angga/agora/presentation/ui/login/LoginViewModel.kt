package com.angga.agora.presentation.ui.login

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
import com.angga.agora.presentation.ui.register.RegisterEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    init {
        combine(
            snapshotFlow { state.email.text },
            snapshotFlow { state.password.text}
        ) { email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(
                    email.toString().trim()
                ) && password.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            LoginAction.OnLoginClick -> { login() }
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)

            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString().trim()
            )

            state = state.copy(isLoggingIn = false)

            when(result) {
                is Result.Failed -> {
                    eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                }

                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}