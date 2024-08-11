package com.angga.agora.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.angga.agora.presentation.ui.components.AgoraActionButton
import com.angga.agora.presentation.ui.theme.AgoraTheme

@Composable
fun HomeScreenRoot(
    onJoinClick : () -> Unit
) {
    HomeScreen(
        onJoinClick = {
            onJoinClick()
        }
    )
}

@Composable
private fun HomeScreen(
    onJoinClick : () -> Unit
) {
    AgoraActionButton(
        text = "Join",
        isLoading = false,
        onClick = {
            onJoinClick()
        }
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AgoraTheme {
        HomeScreen(
            onJoinClick = {}
        )
    }
}