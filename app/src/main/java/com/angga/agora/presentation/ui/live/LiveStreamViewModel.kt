package com.angga.agora.presentation.ui.live

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.angga.agora.domain.live.LiveStreamEngine
import com.angga.agora.domain.live.LiveStreamEventHandler
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

    val eventHandler = object : LiveStreamEventHandler {
        override fun onUserJoined(userId: Int) {
            state = state.copy(
                remoteUid = userId
            )
        }

        override fun onJoinChannel(userId: Int) {
            state = state.copy(
                isJoined = true,
                localUid = userId
            )
        }

        override fun onLeaveChannel() {
            state = state.copy(
                isJoined = false,
                localUid = 0,
                remoteUid = 0
            )
        }

        override fun onClientRoleChange(newRole: Int) {
            state = state.copy(
                roleType = newRole
            )
        }

        override fun onUserOffline(userId: Int) {

        }
    }

    lateinit var liveStreamEngine: LiveStreamEngine

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
        liveStreamRepository.leaveChannel(liveStreamEngine)
    }

    private fun joinChannel() {
        liveStreamRepository.joinChannel(
            state.channelName.text.toString().trim(),
            state.roleType,
            liveStreamEngine
        )
    }

    private fun createEngine() {
        liveStreamEngine = liveStreamRepository.initialize(eventHandler = eventHandler)
        liveStreamRepository.setVideoConfiguration(liveStreamEngine)
    }

}