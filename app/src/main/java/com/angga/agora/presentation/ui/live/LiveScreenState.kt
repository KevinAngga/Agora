package com.angga.agora.presentation.ui.live

import androidx.compose.foundation.text.input.TextFieldState
import io.agora.rtc2.Constants

data class LiveScreenState (
    var channelName : TextFieldState = TextFieldState(),
    var isLoading : Boolean = false,
    var isJoined : Boolean = false,
    var roleType : Int = Constants.CLIENT_ROLE_AUDIENCE,
    var localUid : Int = 0,
    var remoteUid : Int = 0,
    var localLarge : Boolean = false

)