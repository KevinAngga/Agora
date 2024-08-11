package com.angga.agora.domain.live

import com.angga.agora.domain.model.VideoConfiguration

interface LiveStreamEngine {
    fun joinChannel()
    fun leaveChannel()
    fun setConfiguration()
}