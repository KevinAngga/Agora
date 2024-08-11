package com.angga.agora.presentation.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.angga.agora.R
import com.angga.agora.presentation.ui.components.AgoraEmptyView
import com.angga.agora.presentation.ui.components.EmptyChat
import com.angga.agora.presentation.ui.components.EmptyVideo
import com.angga.agora.presentation.ui.theme.AgoraTheme

@Composable
fun ChatScreenRoot() {
    ChatScreen()
}

@Composable
private fun ChatScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AgoraEmptyView(
            imageVector = EmptyChat,
            title = stringResource(id = R.string.empty_chat_title),
            subtitle = stringResource(id = R.string.empty_chat_subtitle)
        )
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
   AgoraTheme {
       ChatScreen()
   }
}