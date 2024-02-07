package com.ukdev.carcadasalborghetti.domain.media

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.model.PlaybackState
import kotlinx.coroutines.flow.StateFlow

interface MediaHandlerV2 {

    val playbackState: StateFlow<PlaybackState>

    suspend fun play(media: MediaV2)

    fun stop()

    suspend fun share(media: MediaV2)
}
