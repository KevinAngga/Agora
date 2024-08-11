package com.angga.agora.presentation.ui.live

sealed interface LiveScreenAction {
    data object OnLiveClick : LiveScreenAction
    data class OnLeaveClick(val localUid : Int, val remoteUid: Int) : LiveScreenAction
    data class OnJoinChannel(val isJoin : Boolean) : LiveScreenAction
    data class OnChangeClientRole(val newRole : Int) : LiveScreenAction
    data class LocalUidChange(val localUid : Int) : LiveScreenAction
    data class RemoteUidChange(val remoteUid : Int) : LiveScreenAction
}