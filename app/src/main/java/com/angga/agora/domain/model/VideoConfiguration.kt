package com.angga.agora.domain.model

data class VideoConfiguration(
    val width: Int,
    val height: Int,
    val frameRate: Int,
    val bitrate: Int,
    val orientationMode: OrientationMode
)
