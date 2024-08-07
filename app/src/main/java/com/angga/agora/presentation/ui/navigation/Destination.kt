package com.angga.agora.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object Login

    @Serializable
    data object Register

    @Serializable
    data object Auth
}