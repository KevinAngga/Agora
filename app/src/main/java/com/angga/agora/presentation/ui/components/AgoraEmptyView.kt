package com.angga.agora.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.angga.agora.R
import com.angga.agora.presentation.ui.theme.AgoraTheme

@Composable
fun AgoraEmptyView(
    modifier: Modifier = Modifier,
    imageVector : ImageVector,
    title : String,
    subtitle : String
) {
    Column(
        modifier = modifier
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = subtitle
        )
    }
}


@Preview
@Composable
fun AgoraEmptyViewPreview() {
    AgoraTheme {
        AgoraEmptyView(
            modifier = Modifier.fillMaxSize(),
            imageVector = EmptyHome,
            title = stringResource(id = R.string.empty_home_title),
            subtitle = stringResource(id = R.string.empty_home_subtitle)
        )
    }
}