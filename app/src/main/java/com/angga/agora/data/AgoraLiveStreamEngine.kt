package com.angga.agora.data

import com.angga.agora.domain.live.LiveStreamEngine
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.Constants.AUDIO_SCENARIO_GAME_STREAMING
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoEncoderConfiguration

class AgoraLiveStreamEngine(private val rtcEngine: RtcEngine) : LiveStreamEngine {
    fun getRtcEngine(): RtcEngine {
        return rtcEngine
    }

    fun joinChannelWithName(channelName : String, roleType : Int) {
        val mediaOptions = ChannelMediaOptions().apply {
            channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
            clientRoleType = roleType
        }
        rtcEngine.joinChannel(null, channelName,0, mediaOptions)
    }

    override fun joinChannel() {
        rtcEngine.startPreview()
    }

    override fun leaveChannel() {
        println("==== leave channel")
        rtcEngine.stopPreview()
        rtcEngine.leaveChannel()
    }

    override fun setConfiguration() {
        rtcEngine.setVideoEncoderConfiguration(
            VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_960x540,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_60,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE
            )
        )
        rtcEngine.setAudioScenario(AUDIO_SCENARIO_GAME_STREAMING)
        rtcEngine.enableVideo()
        rtcEngine.startPreview()
    }

}