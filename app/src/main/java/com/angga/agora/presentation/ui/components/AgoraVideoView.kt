package com.angga.agora.presentation.ui.components

import android.content.Context
import android.view.TextureView
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AgoraVideoView(
    modifier: Modifier = Modifier,
    createView: ((context: Context) -> View)? = null,
    setupVideo: (renderView: View, id: Int) -> Unit,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            createView?.invoke(context) ?: TextureView(context).apply {
                tag = id
                setupVideo(this, id)
            }
        }
    )
}

//AndroidView(
//modifier = Modifier.fillMaxSize(),
//factory = { context ->
//    TextureView(context).apply {
//        viewModel.rtcEngine.setupLocalVideo(
//            VideoCanvas(
//                this,
//                Constants.RENDER_MODE_HIDDEN,
//                0
//            )
//        )
//    }
//}
//)