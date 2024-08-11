package com.angga.agora.domain.live

import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine

interface LiveStreamRepository {
    fun initializeRtcEngine(eventHandler: IRtcEngineEventHandler): RtcEngine
    fun joinChannel(rtcEngine: RtcEngine, channelName: String, mediaOptions: ChannelMediaOptions)
    fun leaveChannel(rtcEngine: RtcEngine)
    fun setVideoConfiguration(rtcEngine: RtcEngine)
    fun setDualStreamMode(rtcEngine: RtcEngine)
}