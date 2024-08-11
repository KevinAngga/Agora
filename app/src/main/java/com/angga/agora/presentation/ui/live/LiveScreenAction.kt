package com.angga.agora.presentation.ui.live

sealed interface LiveScreenAction {
    data object OnLiveClick : LiveScreenAction
    data object OnLeaveClick : LiveScreenAction
    data object OnChangeRole : LiveScreenAction
}