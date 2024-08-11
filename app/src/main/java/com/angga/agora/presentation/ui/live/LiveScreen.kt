package com.angga.agora.presentation.ui.live

import android.content.Context
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.angga.agora.R
import com.angga.agora.presentation.ui.components.AgoraActionButton
import com.angga.agora.presentation.ui.components.AgoraTransparentTextField
import com.angga.agora.presentation.ui.theme.AgoraTheme
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas

@Composable
fun LiveScreenRoot(
    viewModel: LiveStreamViewModel = hiltViewModel(),
) {

    LiveScreen(
        state = viewModel.state,
        rtcEngine = viewModel.rtcEngine,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LiveScreen(
    state: LiveScreenState,
    rtcEngine : RtcEngine? = null,
    onAction: (LiveScreenAction) -> Unit,
) {

    val context = LocalContext.current

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { grantedMap ->
            val allGranted = grantedMap.values.all { it }
            if (allGranted) {
                // Permission is granted
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_LONG).show()
                val mediaOptions = ChannelMediaOptions()
                mediaOptions.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
            } else {
                // Permission is denied
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }

    LaunchedEffect(key1 = true) {
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.CAMERA
            )
        )
    }

    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            onAction(LiveScreenAction.OnLeaveClick)
            RtcEngine.destroy()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {



//            AndroidView(
//                modifier = Modifier.fillMaxSize(),
//                factory = { context ->
//                    TextureView(context).apply {
//                        if (state.roleType == Constants.CLIENT_ROLE_AUDIENCE) {
//                            rtcEngine?.setupRemoteVideo(
//                                VideoCanvas(
//                                    this,
//                                    Constants.RENDER_MODE_HIDDEN,
//                                    0
//                                )
//                            )
//                        } else {
//                            rtcEngine?.setupLocalVideo(
//                                VideoCanvas(
//                                    this,
//                                    Constants.RENDER_MODE_HIDDEN,
//                                    0
//                                )
//                            )
//                        }
//                    }
//                },
//                update = {
//                    rtcEngine?.setupRemoteVideo(
//                        VideoCanvas(
//                            it,
//                            Constants.RENDER_MODE_HIDDEN,
//                            0
//                        )
//                    )
//                }
//            )

            TwoVideoView(
                modifier = Modifier.fillMaxHeight(),
                localUid = state.localUid,
                remoteUid = state.remoteUid,
                localRender = { view, uid, _ ->
                    rtcEngine?.setupLocalVideo(
                        VideoCanvas(
                            view,
                            Constants.RENDER_MODE_HIDDEN,
                            uid
                        )
                    )
                },

                remoteRender = { view, uid, _ ->
                    rtcEngine?.setupRemoteVideo(
                        VideoCanvas(
                            view,
                            Constants.RENDER_MODE_HIDDEN,
                            uid
                        )
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Blue.copy(alpha = 0.1f))
                    .align(Alignment.TopStart)
            ) {
                AgoraTransparentTextField(
                    state = state.channelName,
                    hint = stringResource(id = R.string.add_title_to_chat)
                )
            }
//
//            if (!state.isJoined) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.BottomStart)
//                        .padding(horizontal = 32.dp, vertical = 16.dp)
//                ) {
//                    AgoraActionButton(
//                        text = stringResource(id = R.string.go_live),
//                        isLoading = state.isLoading,
//                        onClick = {
//                            onAction(LiveScreenAction.OnLiveClick)
//                        }
//                    )
//                }
//            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                AgoraActionButton(
                    text = stringResource(id = R.string.go_live),
                    isLoading = state.isLoading,
                    onClick = {
                        onAction(LiveScreenAction.OnLiveClick)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                AgoraActionButton(
                    text = "Changes",
                    isLoading = state.isLoading,
                    onClick = {
                        onAction(LiveScreenAction.OnChangeRole)
                    }
                )
            }
        }
    }
}

@Composable
fun TwoVideoView(
    modifier: Modifier = Modifier,
    localUid: Int,
    remoteUid: Int,
    localPrimary: Boolean = true,
    secondClickable: Boolean = false,
    onSecondClick: () -> Unit = {},
    localCreate: ((context: Context) -> View)? = null,
    remoteCreate: ((context: Context) -> View)? = null,
    localRender: (View, Int, Boolean) -> Unit,
    remoteRender: (View, Int, Boolean) -> Unit,
) {
    val primary: @Composable (Modifier) -> Unit = {
        VideoCell(
            modifier = it,
            id = if (localPrimary) localUid else remoteUid,
            isLocal = localPrimary,
            createView = if (localPrimary) localCreate else remoteCreate,
            setupVideo = if (localPrimary) localRender else remoteRender,
        )
    }
    val second: @Composable (Modifier) -> Unit = {
        VideoCell(
            modifier = if (secondClickable) it.clickable {
                onSecondClick()
            } else it,
            id = if (!localPrimary) localUid else remoteUid,
            isLocal = !localPrimary,
            createView = if (localPrimary) remoteCreate else localCreate,
            setupVideo = if (!localPrimary) localRender else remoteRender,
        )
    }
    Box(modifier = modifier) {
        primary(Modifier.fillMaxSize())
        second(
            Modifier
                .fillMaxSize(0.5f)
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )
    }
}

@Composable
fun VideoCell(
    modifier: Modifier = Modifier,
    id: Int,
    isLocal: Boolean,
    createView: ((context: Context) -> View)? = null,
    setupVideo: (renderView: View, id: Int, isFirstSetup: Boolean) -> Unit,
    overlay: @Composable BoxScope.() -> Unit? = { }
) {
    Box(modifier) {
        if (id != 0) {
            AndroidView(
                factory = { context ->
                    Log.d("VideoCell", "VideoCell: create render view.")
                    createView?.invoke(context) ?: TextureView(context).apply {
                        tag = id
                        setupVideo(this, id, true)
                    }
                },
                update = { view ->
                    if (view.tag != id) {
                        Log.d("VideoCell", "VideoCell: update render view.")
                        view.tag = id
                        setupVideo(view, id, false)
                    }
                })
        }
    }
}

@Preview
@Composable
private fun LiveScreenPreview() {
    AgoraTheme {
        LiveScreen(
            state = LiveScreenState(),
            onAction = {}
        )
    }
}