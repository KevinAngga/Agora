package com.angga.agora.presentation.ui.account

sealed interface AccountScreenAction {
    data object OnLogoutClick : AccountScreenAction
}