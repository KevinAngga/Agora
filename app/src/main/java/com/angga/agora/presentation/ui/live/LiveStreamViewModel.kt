package com.angga.agora.presentation.ui.live

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.angga.agora.domain.live.LiveStreamRepository
import com.angga.agora.presentation.ui.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas
import io.agora.rtc2.video.VideoEncoderConfiguration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveStreamViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val liveStreamRepository: LiveStreamRepository,
) : ViewModel() {

    var state by mutableStateOf(LiveScreenState())
        private set

    private val _roleType = MutableStateFlow(savedStateHandle.toRoute<Destination.Live>().roleType)
    val roleType = _roleType.asStateFlow()

    private val rtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            super.onJoinChannelSuccess(channel, uid, elapsed)
            viewModelScope.launch {
                state = state.copy(
                    isJoined = true,
                    localUid = uid
                )
            }
        }

        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            state = state.copy(
                remoteUid = uid
            )
        }

        override fun onLeaveChannel(stats: RtcStats?) {
            super.onLeaveChannel(stats)
            state = state.copy(
                isJoined = false
            )
        }
    }

    lateinit var rtcEngine: RtcEngine

    init {
        createEngine()
    }


    fun onAction(action: LiveScreenAction) {
        when (action) {
            LiveScreenAction.OnLiveClick -> {
                joinChannel()
            }

            LiveScreenAction.OnLeaveClick -> {
                leaveChannel()
            }

            LiveScreenAction.OnChangeRole -> {
                val roleTypes = if (state.roleType == Constants.CLIENT_ROLE_AUDIENCE) Constants.CLIENT_ROLE_BROADCASTER else { Constants.CLIENT_ROLE_AUDIENCE }
                state = state.copy(
                    roleType = roleTypes
                )
            }
        }
    }

    private fun leaveChannel() {
        liveStreamRepository.leaveChannel(rtcEngine)
    }

    private fun joinChannel() {
        val mediaOptions = ChannelMediaOptions().apply {
            channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
            clientRoleType = state.roleType
        }
        liveStreamRepository.joinChannel(
            rtcEngine,
            state.channelName.text.toString().trim(),
            mediaOptions
        )
    }

    private fun createEngine() {
        rtcEngine = liveStreamRepository.initializeRtcEngine(eventHandler = rtcEventHandler)
        state = state.copy(
            roleType = roleType.value
        )
        liveStreamRepository.setVideoConfiguration(rtcEngine)
    }

}