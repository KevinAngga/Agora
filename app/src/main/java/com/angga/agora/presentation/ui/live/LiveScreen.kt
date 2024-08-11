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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.angga.agora.BuildConfig
import com.angga.agora.R
import com.angga.agora.data.AgoraLiveStreamEngine
import com.angga.agora.domain.live.LiveStreamEngine
import com.angga.agora.presentation.ui.components.AgoraActionButton
import com.angga.agora.presentation.ui.components.AgoraTransparentTextField
import com.angga.agora.presentation.ui.theme.AgoraTheme
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.ClientRoleOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.SimulcastStreamConfig
import io.agora.rtc2.video.VideoCanvas
import io.agora.rtc2.video.VideoEncoderConfiguration

@Composable
fun LiveScreenRoot(
    viewModel: LiveStreamViewModel = hiltViewModel(),
) {
    LiveScreen(
        state = viewModel.state,
    )
}

@Composable
private fun LiveScreen(
    state: LiveScreenState
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    //update with action
    var isJoined by rememberSaveable { mutableStateOf(false) }
    var localLarge by rememberSaveable { mutableStateOf(true) }
    var localUid by rememberSaveable { mutableIntStateOf(0) }
    var remoteUid by rememberSaveable { mutableIntStateOf(0) }
    var clientRole by remember { mutableStateOf(Constants.CLIENT_ROLE_AUDIENCE) }

    val rtcEngine = remember {
        RtcEngine.create(RtcEngineConfig().apply {
            mContext = context
            mAppId = BuildConfig.AGORA_APP_ID
            mEventHandler = object : IRtcEngineEventHandler() {
                override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                    super.onJoinChannelSuccess(channel, uid, elapsed)
                    isJoined = true
                    localUid = uid
                }

                override fun onLeaveChannel(stats: RtcStats?) {
                    super.onLeaveChannel(stats)
                    isJoined = false
                    localUid = 0
                    remoteUid = 0
                }

                override fun onUserJoined(uid: Int, elapsed: Int) {
                    super.onUserJoined(uid, elapsed)
                    remoteUid = uid
                }

                override fun onUserOffline(uid: Int, reason: Int) {
                    super.onUserOffline(uid, reason)
                    if (remoteUid == uid) {
                        remoteUid = 0
                    }
                }

                override fun onRtcStats(stats: RtcStats?) {
                    super.onRtcStats(stats)
                }

                override fun onLocalVideoStats(
                    source: Constants.VideoSourceType?,
                    stats: LocalVideoStats?,
                ) {
                    super.onLocalVideoStats(source, stats)
                }


                override fun onClientRoleChanged(
                    oldRole: Int,
                    newRole: Int,
                    newRoleOptions: ClientRoleOptions?,
                ) {
                    super.onClientRoleChanged(oldRole, newRole, newRoleOptions)
                    clientRole = newRole
                }
            }
        }).apply {
            setVideoEncoderConfiguration(
                VideoEncoderConfiguration(
                    VideoEncoderConfiguration.VD_960x540,
                    VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_60,
                    VideoEncoderConfiguration.STANDARD_BITRATE,
                    VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE
                )
            )
            enableVideo()
            setDualStreamMode(
                Constants.SimulcastStreamMode.ENABLE_SIMULCAST_STREAM,
                SimulcastStreamConfig(
                    VideoEncoderConfiguration.VideoDimensions(
                        100, 100
                    ), 100, 15
                )
            )
        }
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { grantedMap ->
            val allGranted = grantedMap.values.all { it }
            if (allGranted) {
                // Permission is granted
                rtcEngine.startPreview()
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
            rtcEngine.stopPreview()
            rtcEngine.leaveChannel()
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

            TwoVideoView(
                modifier = Modifier.fillMaxHeight(),
                localUid = localUid,
                remoteUid = remoteUid,
                localPrimary = localLarge || remoteUid == 0,
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
                },
                role = clientRole
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                AgoraActionButton(
                    text = if (isJoined) stringResource(id = R.string.leave) else stringResource(id = R.string.go_live),
                    isLoading = false,
                    onClick = {
                        keyboard?.hide()
                        focusManager.clearFocus()
                        if (isJoined) {
                            rtcEngine.stopPreview()
                            rtcEngine.leaveChannel()
                        } else {
                            val mediaOptions = ChannelMediaOptions()
                            mediaOptions.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
                            rtcEngine.joinChannel(
                                null,
                                state.channelName.text.toString().trim(),
                                0,
                                mediaOptions
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                AgoraActionButton(
                    text = if (clientRole == Constants.CLIENT_ROLE_AUDIENCE)
                        stringResource(id = R.string.be_broadcaster) else stringResource(
                        id = R.string.be_audience
                    ),
                    enabled = isJoined,
                    isLoading = false,
                    onClick = {
                        if (clientRole == Constants.CLIENT_ROLE_AUDIENCE) {
                            rtcEngine?.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
                        } else {
                            rtcEngine?.setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
                        }
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
    role: Int,
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
        if (remoteUid == 0) {
            primary(Modifier.fillMaxSize())
        }

        if (remoteUid != 0 && role == Constants.CLIENT_ROLE_AUDIENCE) {
            second(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun VideoCell(
    modifier: Modifier = Modifier,
    id: Int,
    isLocal: Boolean,
    createView: ((context: Context) -> View)? = null,
    setupVideo: (renderView: View, id: Int, isFirstSetup: Boolean) -> Unit,
    overlay: @Composable BoxScope.() -> Unit? = { },
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
        )
    }
}