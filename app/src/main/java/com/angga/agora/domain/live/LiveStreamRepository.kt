package com.angga.agora.domain.live

interface LiveStreamRepository {
    fun initialize(eventHandler: LiveStreamEventHandler): LiveStreamEngine
    fun joinChannel(channelName : String, roleType : Int, liveStreamEngine: LiveStreamEngine)
    fun leaveChannel(liveStreamEngine: LiveStreamEngine)
    fun setVideoConfiguration(liveStreamEngine: LiveStreamEngine)
    fun setDualStreamMode(liveStreamEngine: LiveStreamEngine)
}