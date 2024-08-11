package com.angga.agora.presentation.ui.live

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.angga.agora.domain.live.LiveStreamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LiveStreamViewModel @Inject constructor(
    private val liveStreamRepository: LiveStreamRepository,
) : ViewModel() {

    var state by mutableStateOf(LiveScreenState())
        private set

}