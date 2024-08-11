package com.angga.agora.presentation.ui.navigation

import io.agora.rtc2.Constants
import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object Login

    @Serializable
    data object Register

    @Serializable
    data object Auth

    @Serializable
    data object Home

    @Serializable
    data object Video

    @Serializable
    data class Live(
        var roleType : Int = Constants.CLIENT_ROLE_AUDIENCE
    )

    @Serializable
    data object Chat

    @Serializable
    data object Account


    @Serializable
    data object HomePage
}