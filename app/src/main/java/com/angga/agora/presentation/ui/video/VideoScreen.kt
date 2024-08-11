package com.angga.agora.presentation.ui.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.angga.agora.R
import com.angga.agora.presentation.ui.components.AgoraEmptyView
import com.angga.agora.presentation.ui.components.EmptyVideo
import com.angga.agora.presentation.ui.theme.AgoraTheme


@Composable
fun VideoScreenRoot() {
    VideoScreen()
}

@Composable
private fun VideoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AgoraEmptyView(
            imageVector = EmptyVideo,
            title = stringResource(id = R.string.empty_video_title),
            subtitle = stringResource(id = R.string.empty_video_subtitle)
        )
    }
}

@Preview
@Composable
private fun VideoScreenPreview() {
    AgoraTheme {
        VideoScreen()
    }
}