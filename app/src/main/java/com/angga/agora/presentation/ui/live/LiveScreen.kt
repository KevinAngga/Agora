package com.angga.agora.presentation.ui.live

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.angga.agora.presentation.ui.theme.AgoraTheme

@Composable
fun LiveScreenRoot(

) {
    LiveScreen(

    )
}

@Composable
private fun LiveScreen(

) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "this is Live Screen")
    }
}

@Preview
@Composable
private fun LiiveScreenPreview() {
   AgoraTheme {
       LiveScreen()
   }
}