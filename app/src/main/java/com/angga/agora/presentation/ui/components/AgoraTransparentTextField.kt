package com.angga.agora.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angga.agora.presentation.ui.theme.AgoraTheme

@Composable
fun AgoraTransparentTextField(
    state: TextFieldState,
    hint: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
) {

    var isFocused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        BasicTextField(
            state = state,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            lineLimits = TextFieldLineLimits.SingleLine,//determine single or multi line
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground), //cursor color
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Transparent)
                .padding(12.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            decorator = { innerBox ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = hint,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.4f
                                )
                            )
                        }
                        innerBox()
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun AgoraTransparentTextFieldPreview() {
    AgoraTheme {
        AgoraTransparentTextField(
            state = rememberTextFieldState(),
            hint = "example@test.com",
            modifier = Modifier.fillMaxWidth()
        )
    }
}