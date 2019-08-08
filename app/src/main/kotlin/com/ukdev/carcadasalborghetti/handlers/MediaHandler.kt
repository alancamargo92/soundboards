package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.view.ViewLayer
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class MediaHandler(
        protected val callback: MediaCallback,
        protected val view: ViewLayer
) : KoinComponent {

    protected val context by inject<Context>()

    abstract fun play(media: Media)
    abstract fun stop()
    abstract fun share(media: Media, mediaType: MediaType)
    abstract fun isPlaying(): Boolean

    protected fun createMediaPlayer(uri: Uri): MediaPlayer {
        return MediaPlayer.create(context, uri).apply {
            if (isPlaying) {
                stop()
                callback.onStopPlayback()
                start()
                callback.onStartPlayback()
            } else {
                start()
                callback.onStartPlayback()
            }

            setOnCompletionListener { callback.onStopPlayback() }
        }
    }

}