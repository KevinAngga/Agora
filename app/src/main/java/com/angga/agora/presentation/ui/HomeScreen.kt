package com.angga.agora.presentation.ui

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
import com.angga.agora.presentation.ui.components.EmptyHome
import com.angga.agora.presentation.ui.theme.AgoraTheme

@Composable
fun HomeScreenRoot() {
    HomeScreen()
}

@Composable
private fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AgoraEmptyView(
            imageVector = EmptyHome,
            title = stringResource(id = R.string.empty_home_title),
            subtitle = stringResource(id = R.string.empty_home_subtitle)
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AgoraTheme {
        HomeScreen(
        )
    }
}