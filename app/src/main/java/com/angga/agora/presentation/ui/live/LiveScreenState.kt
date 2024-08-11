package com.angga.agora.presentation.ui.live

import androidx.compose.foundation.text.input.TextFieldState
import io.agora.rtc2.Constants

data class LiveScreenState (
    val channelName : TextFieldState = TextFieldState(),
    val isLoading : Boolean = false,
    val isJoined : Boolean = false,
    val roleType : Int = Constants.CLIENT_ROLE_BROADCASTER,
    val localUid : Int = 0,
    val remoteUid : Int = 0

)