package com.angga.agora.presentation.ui.live

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LiveStreamViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(LiveScreenState())
        private set

    fun onAction(action: LiveScreenAction) {
        when (action) {
            LiveScreenAction.OnLiveClick -> TODO()
            is LiveScreenAction.OnChangeClientRole -> {
                state = state.copy(
                    clientRole = action.newRole
                )
            }

            is LiveScreenAction.OnJoinChannel -> {
                state = state.copy(
                    isJoined = action.isJoin
                )
            }

            is LiveScreenAction.LocalUidChange -> {
                state = state.copy(
                    localUid = action.localUid
                )
            }

            is LiveScreenAction.OnLeaveClick -> {
                state = state.copy(
                    localUid = action.localUid,
                    remoteUid = action.remoteUid
                )
            }

            is LiveScreenAction.RemoteUidChange -> {
                state = state.copy(
                    remoteUid = action.remoteUid
                )
            }
        }
    }

}