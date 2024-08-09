package com.angga.agora.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AgoraMenuItem(
    modifier: Modifier,
    label : String,
    drawableRes: Int,
    isSelected : Boolean = false,
    onClick : () -> Unit
) {
    Column(
        modifier = modifier
            .height(80.dp)
            .background(Color.White)
            .clip(CircleShape)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (label.isNotEmpty()) {
            Text(
                text = label,
                color = if (isSelected) { MaterialTheme.colorScheme.onBackground } else { MaterialTheme.colorScheme.onSurface }
            )
        }

    }
}