package com.angga.agora.data

import android.content.Context
import com.angga.agora.BuildConfig
import com.angga.agora.domain.live.LiveStreamEngine
import com.angga.agora.domain.live.LiveStreamEventHandler
import com.angga.agora.domain.live.LiveStreamRepository
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.ClientRoleOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.Constants.AUDIO_SCENARIO_GAME_STREAMING
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.SimulcastStreamConfig
import io.agora.rtc2.video.VideoEncoderConfiguration
import javax.inject.Inject

/**
 *
 *[not testing it so it wont work] This why you will find the unused code Like LiveStreamRepositoryImpl
 *  and other, this only for showing that we can use this way but is more complicated
 *  and if someday we want to change the SDK it must change from Data, Domain,
 * Presentation Layer, but if we used the current running implementation
 * if we change the SDK it will only affected the presentation Layer
 * **/
class LiveStreamRepositoryImpl @Inject constructor(
    val appContext : Context
) : LiveStreamRepository {
    override fun initialize(eventHandler: LiveStreamEventHandler): LiveStreamEngine {
        val agoraEventHandler = object : IRtcEngineEventHandler() {
            override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                super.onJoinChannelSuccess(channel, uid, elapsed)
                eventHandler.onJoinChannel(uid)
            }

            override fun onUserJoined(uid: Int, elapsed: Int) {
                super.onUserJoined(uid, elapsed)
                eventHandler.onUserJoined(uid)
            }

            override fun onLeaveChannel(stats: RtcStats?) {
                super.onLeaveChannel(stats)
                eventHandler.onLeaveChannel()
            }

            override fun onUserOffline(uid: Int, reason: Int) {
                super.onUserOffline(uid, reason)
            }

            override fun onClientRoleChanged(
                oldRole: Int,
                newRole: Int,
                newRoleOptions: ClientRoleOptions?,
            ) {
                super.onClientRoleChanged(oldRole, newRole, newRoleOptions)
                eventHandler.onClientRoleChange(newRole)
            }
        }

        val rtcEngine = RtcEngine.create(
            RtcEngineConfig().apply {
                mContext = appContext
                mAppId = BuildConfig.AGORA_APP_ID
                mEventHandler = agoraEventHandler
            }
        )
        return AgoraLiveStreamEngine(rtcEngine)
    }

    override fun joinChannel(channelName : String, roleType : Int, liveStreamEngine: LiveStreamEngine) {
        val agoraEngine = liveStreamEngine as AgoraLiveStreamEngine
        agoraEngine.joinChannelWithName(channelName, roleType)
    }

    override fun leaveChannel(liveStreamEngine: LiveStreamEngine) {
        liveStreamEngine.leaveChannel()
    }

    override fun setVideoConfiguration(liveStreamEngine: LiveStreamEngine) {
        liveStreamEngine.setConfiguration()
        setDualStreamMode(liveStreamEngine)
    }

    override fun setDualStreamMode(liveStreamEngine: LiveStreamEngine) {
        val agoraEngine = liveStreamEngine as AgoraLiveStreamEngine
        val rtcEngine = agoraEngine.getRtcEngine()

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