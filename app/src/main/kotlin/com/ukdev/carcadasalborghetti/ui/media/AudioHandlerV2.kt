package com.ukdev.carcadasalborghetti.ui.media

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSourceV2
import com.ukdev.carcadasalborghetti.data.tools.orFalse
import com.ukdev.carcadasalborghetti.domain.media.MediaHandlerV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.model.PlaybackState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AudioHandlerV2 @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remoteDataSource: MediaRemoteDataSourceV2
) : MediaHandlerV2 {

    private var mediaPlayer: MediaPlayer? = null

    private val _playbackState = MutableStateFlow(PlaybackState())

    override val playbackState: StateFlow<PlaybackState> = _playbackState

    override suspend fun play(media: MediaV2) {
        mediaPlayer?.release()
        val uri = Uri.parse(media.id)
        mediaPlayer = createMediaPlayer(uri)
        _playbackState.update { it.onPlaying() }
    }

    override fun stop() {
        mediaPlayer?.stop()
        _playbackState.update { it.onFinishedPlaying() }
    }

    override suspend fun share(media: MediaV2) {
        TODO("Not yet implemented")
    }

    private fun createMediaPlayer(uri: Uri): MediaPlayer {
        return MediaPlayer.create(context, uri).apply {
            if (this?.isPlaying.orFalse()) {
                stop()
                start()
            } else {
                start()
            }

            setOnCompletionListener {
                _playbackState.update { it.onFinishedPlaying() }
            }
        }
    }
}
