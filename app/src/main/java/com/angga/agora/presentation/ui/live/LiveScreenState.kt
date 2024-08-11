package com.angga.agora.presentation.ui.live

import androidx.compose.foundation.text.input.TextFieldState
import io.agora.rtc2.Constants

data class LiveScreenState (
    var channelName : TextFieldState = TextFieldState(),
    val clientRole : Int = Constants.CLIENT_ROLE_AUDIENCE,
    val isJoined : Boolean = false,
    val localLarge : Boolean = true,
    val localUid : Int = 0,
    val remoteUid : Int = 0
)