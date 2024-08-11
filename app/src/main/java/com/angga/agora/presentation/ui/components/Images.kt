package com.angga.agora.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.angga.agora.R

val EmptyChat: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.empty_chat)

val EmptyHome: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.empty_home)

val EmptyVideo: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.empty_video)