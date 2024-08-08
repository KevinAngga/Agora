package com.angga.agora.presentation.ui.register

import com.angga.agora.presentation.ui.utils.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
    data class Error(val error : UiText) : RegisterEvent
}