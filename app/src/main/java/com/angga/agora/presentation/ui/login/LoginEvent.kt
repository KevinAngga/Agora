package com.angga.agora.presentation.ui.login

import com.angga.agora.presentation.ui.utils.UiText

sealed interface LoginEvent {
    data class Error(val error : UiText) : LoginEvent
    data object LoginSuccess : LoginEvent
}