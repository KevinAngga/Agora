package com.angga.agora.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.angga.agora.presentation.ui.theme.AgoraTheme

@Composable
fun HomeScreenRoot() {
    HomeScreen(

    )
}

@Composable
private fun HomeScreen(

) {
    Text(text = "main")
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AgoraTheme {
        HomeScreen()
    }
}