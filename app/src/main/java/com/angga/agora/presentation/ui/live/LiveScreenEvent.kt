package com.angga.agora.presentation.ui.live

sealed interface LiveScreenEvent {
    data class SuccessJoinChannel(val joined : Boolean): LiveScreenEvent
}