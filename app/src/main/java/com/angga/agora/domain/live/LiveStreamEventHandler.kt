package com.angga.agora.domain.live

interface LiveStreamEventHandler {
    fun onUserJoined(userId: Int)
    fun onJoinChannel(userId: Int)
    fun onLeaveChannel()
    fun onClientRoleChange(newRole : Int)
    fun onUserOffline(userId: Int)
}