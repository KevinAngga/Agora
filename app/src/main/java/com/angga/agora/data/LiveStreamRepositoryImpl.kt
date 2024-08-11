package com.angga.agora.data

import android.content.Context
import com.angga.agora.BuildConfig
import com.angga.agora.domain.live.LiveStreamRepository
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.Constants.AUDIO_SCENARIO_GAME_STREAMING
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.SimulcastStreamConfig
import io.agora.rtc2.video.VideoEncoderConfiguration
import javax.inject.Inject

class LiveStreamRepositoryImpl @Inject constructor(
    val appContext : Context
) : LiveStreamRepository {

    override fun initializeRtcEngine(eventHandler: IRtcEngineEventHandler): RtcEngine {
        return RtcEngine.create(
            RtcEngineConfig().apply {
                mContext = appContext
                mAppId = BuildConfig.AGORA_APP_ID
                mEventHandler = eventHandler
            }
        )
    }

    override fun joinChannel(rtcEngine: RtcEngine, channelName: String, mediaOptions: ChannelMediaOptions) {
        rtcEngine.joinChannel(null, channelName,0, mediaOptions)
    }

    override fun leaveChannel(rtcEngine: RtcEngine) {
        rtcEngine.stopPreview()
        rtcEngine.leaveChannel()
    }

    override fun setVideoConfiguration(rtcEngine: RtcEngine) {
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
//        setDualStreamMode(rtcEngine)


    }

    override fun setDualStreamMode(rtcEngine: RtcEngine) {
        rtcEngine.setDualStreamMode(
            Constants.SimulcastStreamMode.ENABLE_SIMULCAST_STREAM,
            SimulcastStreamConfig(
                VideoEncoderConfiguration.VideoDimensions(100, 100),
                100,
                15
            )
        )
    }
}