package com.angga.agora.presentation.ui.navigation

import com.angga.agora.R
import com.angga.agora.presentation.ui.utils.Constant

enum class BottomNavigation(
    val label : String,
    val icon : Int,
    val route: Any
) {
    HOME(Constant.HOME, R.drawable.home, Destination.Home),
    VIDEO(Constant.VIDEO, R.drawable.video, Destination.Video),
    LIVE("", R.drawable.camera, Destination.Live),
    CHAT(Constant.CHAT, R.drawable.chat, Destination.Chat),
    ACCOUNT(Constant.ACCOUNT, R.drawable.account, Destination.Account),
}